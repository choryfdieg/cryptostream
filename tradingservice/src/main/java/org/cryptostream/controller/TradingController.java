package org.cryptostream.controller;

import org.cryptostream.controller.api.TradingAPI;
import org.cryptostream.model.entity.Balance;
import org.cryptostream.model.entity.Transaction;
import org.cryptostream.model.entity.TransactionRequest;
import org.cryptostream.model.entity.User;
import org.cryptostream.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trading")
public class TradingController implements TradingAPI {

    @Autowired
    private final TradingService tradingService;
    
    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }
    
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(
            TransactionRequest request) {
            
        Transaction transaction = tradingService.createTransaction(
                request.getUserId(),
                request.getTransactionType(),
                request.getAmount(),
                request.getCurrency()
        );

        return ResponseEntity.ok(transaction);
    }
    
    @GetMapping("/transactions/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = tradingService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsById(Integer userId) {
        List<Transaction> transactions = tradingService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/balances/{userId}")
    public ResponseEntity<List<Balance>> getBalancesByUserId(Integer userId) {
        List<Balance> balances = tradingService.getBalancesByUserId(userId);
        return ResponseEntity.ok(balances);
    }
}