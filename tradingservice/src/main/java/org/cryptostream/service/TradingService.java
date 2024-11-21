package org.cryptostream.service;

import org.cryptostream.model.*;
import org.cryptostream.repository.BalanceRepository;
import org.cryptostream.repository.TransactionRepository;
import org.cryptostream.repository.UserRepository;
import org.cryptostream.services.ICoingeckoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public Transaction createTransaction(User user, TransactionType type, BigDecimal amount, String currency) {
    
        Optional<User> userOpt = userRepository.findById(1);
    
        user = userOpt.get();
    
        if (type.equals(TransactionType.SELL) && !hasSufficientToSell(user, amount, currency)) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la venta");
        }
    
        if (type.equals(TransactionType.BUY) && !hasSufficientToBuy(user, amount, currency)) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar la compra");
        }
        
        Transaction transaction = Transaction.builder()
                .user(user)
                .transactionType(type)
                .amount(amount)
                .currency(currency)
                .build();
                
        return transactionRepository.save(transaction);
    }

    public List<Balance> getBalancesForUser(Integer userId) {
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
        PriceResponse priceResponse = coingeckoClient.getPriceByCoinId(currency);
    
        Integer value = priceResponse.getPrices().get(currency).get("usd");
    
        return BigDecimal.valueOf(value);
    }
}