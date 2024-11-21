package org.cryptostream.controller;

import org.cryptostream.model.Balance;
import org.cryptostream.model.Transaction;
import org.cryptostream.model.TransactionRequest;
import org.cryptostream.model.User;
import org.cryptostream.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/crypto-trading")
public class TradingController {

    @Autowired
    private final TradingService tradingService;
    
    public TradingController(TradingService tradingService) {
        this.tradingService = tradingService;
    }
    
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(
            @RequestBody TransactionRequest request) {
            
        User user = getUserFromRequest();

        Transaction transaction = tradingService.createTransaction(
                user,
                request.getTransactionType(),
                request.getAmount(),
                request.getCurrency()
        );

        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/balances/{userId}")
    public ResponseEntity<List<Balance>> getBalancesForUser(@PathVariable Integer userId) {
        List<Balance> balances = tradingService.getBalancesForUser(userId);
        return ResponseEntity.ok(balances);
    }

    private User getUserFromRequest() {
        
        // get fron jwt
        
        
    
        return User.builder().build(); 
    }
}