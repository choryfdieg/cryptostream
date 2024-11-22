package org.cryptostream.service;

import org.cryptostream.config.CoinConfig;
import org.cryptostream.model.*;
import org.cryptostream.model.entity.Balance;
import org.cryptostream.model.entity.Transaction;
import org.cryptostream.model.entity.TransactionType;
import org.cryptostream.model.entity.User;
import org.cryptostream.repository.BalanceRepository;
import org.cryptostream.repository.TransactionRepository;
import org.cryptostream.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TradingService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BalanceRepository balanceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ICoingeckoClient coingeckoClient;
    
    @Autowired
    private NotificationProducer notificationProducer;
    
    @Transactional
    public Transaction createTransaction(String userId, TransactionType type, BigDecimal amount, String currency) {
    
        Optional<User> userOpt = userRepository.findByEmail(userId);
    
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
    
        User user = userOpt.get();
    
        if (type.equals(TransactionType.SELL) && !hasSufficientToSell(user, amount, currency)) {
            throw new IllegalArgumentException("No tiene saldo suficiente para realizar la venta");
        }
    
        if (type.equals(TransactionType.BUY) && !hasSufficientToBuy(user, amount, currency)) {
            throw new IllegalArgumentException("No tiene saldo suficiente en USD para realizar la compra");
        }
        
        Transaction transaction = Transaction.builder()
                .user(user)
                .transactionType(type)
                .amount(amount)
                .currency(currency)
                .build();
                
        transactionRepository.save(transaction);
    
        updateUserBalance(user, amount, currency, type);
    
        String notificationMessage = String.format("El usuario %s ha registrado una %s. Moneda: %s, Cantidad: %s ",
            user.getName(), type.name(), currency, amount);
            
        notificationProducer.sendNotification(notificationMessage);
        
        return transaction;
    }
    
    private void updateUserBalance(User user, BigDecimal amount, String criptoCurrency, TransactionType type) {
        
        if(type.equals(TransactionType.BUY)){
            updateBalanceWhenBuy(user, amount, criptoCurrency);
        }
    
        if(type.equals(TransactionType.SELL)){
            updateBalanceWhenSell(user, amount, criptoCurrency);
        }
        
    }
    
    public void updateBalanceWhenBuy(User user, BigDecimal amount, String criptoCurrency) {
        
        Balance usdBalance = balanceRepository.findByUserIdAndCurrency(user.getId(), "USD");
        Balance cryptoBalance = balanceRepository.findByUserIdAndCurrency(user.getId(), criptoCurrency);
        
        BigDecimal cryptoPrice = getCryptoPrice(criptoCurrency);
        BigDecimal totalCost = amount.multiply(cryptoPrice);
        
        usdBalance.setAmount(usdBalance.getAmount().subtract(totalCost));
        
        if (cryptoBalance == null) {
            cryptoBalance = Balance.builder()
                    .user(user)
                    .currency(criptoCurrency)
                    .amount(BigDecimal.ZERO)
                    .build();
        }
        
        cryptoBalance.setAmount(cryptoBalance.getAmount().add(amount));
        
        balanceRepository.save(usdBalance);
        balanceRepository.save(cryptoBalance);
    }
    
    public void updateBalanceWhenSell(User user, BigDecimal amount, String cryptoCurrency) {
    
        Balance usdBalance = balanceRepository.findByUserIdAndCurrency(user.getId(), "USD");
        Balance cryptoBalance = balanceRepository.findByUserIdAndCurrency(user.getId(), cryptoCurrency);
        
        BigDecimal cryptoPrice = getCryptoPrice(cryptoCurrency);
        BigDecimal totalRevenue = amount.multiply(cryptoPrice);
        
        cryptoBalance.setAmount(cryptoBalance.getAmount().subtract(amount));
        
        if (usdBalance == null) {
            usdBalance = Balance.builder()
                    .user(user)
                    .currency("USD")
                    .amount(BigDecimal.ZERO)
                    .build();
        }
        
        usdBalance.setAmount(usdBalance.getAmount().add(totalRevenue));
        
        balanceRepository.save(usdBalance);
        balanceRepository.save(cryptoBalance);
    
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public List<Transaction> getTransactionsByUserId(Integer userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Balance> getBalancesByUserId(Integer userId) {
        return balanceRepository.findByUserId(userId);
    }
    
    private BigDecimal geBalanceForUserAndCurrency(Integer userId, String currency) {
        Balance currencyBalance = balanceRepository.findByUserIdAndCurrency(userId, currency);
        return currencyBalance != null ? currencyBalance.getAmount() : BigDecimal.ZERO;
    }
    
    private boolean hasSufficientToBuy(User user, BigDecimal amount, String cryptoCurrency) {
        
        // consultar valor en USD de la moneda en Coingecko
        BigDecimal cryptoPrice = getCryptoPrice(cryptoCurrency);
        
        // multiplicar la cantidad por el precio en USD
        BigDecimal requiredAmount = amount.multiply(cryptoPrice);
        
        // obtener el saldo en USD del usuario
        BigDecimal usdBalance = geBalanceForUserAndCurrency(user.getId(), "USD");
        
        // validar si tiene suficiente saldo
        if(usdBalance.compareTo(requiredAmount) >= 0){
            return true;
        }
        
        return false;
    }
    
    private boolean hasSufficientToSell(User user, BigDecimal amount, String cryptoCurrency) {
    
        // obtener el saldo de la moneda que el usuario desea vender
        BigDecimal currencyBalance = geBalanceForUserAndCurrency(user.getId(), cryptoCurrency);
        
        // verificar si tiene suficiente para vender
        if(currencyBalance.compareTo(amount) >= 0){
            return true;
        }
        
        return false;
    }
    
    private BigDecimal getCryptoPrice(String currency) {
    
        String coingeckoId = CoinConfig.getCryptoIdsMap().get(currency);
    
        PriceResponse priceResponse = coingeckoClient.getPriceByCoinId(coingeckoId);
    
        BigDecimal value = BigDecimal.valueOf(((Number)priceResponse.getPrices().get(coingeckoId).get("usd")).doubleValue());
    
        return value;
    }
}