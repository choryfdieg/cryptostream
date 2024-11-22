package org.cryptostream.model.entity;

import java.math.BigDecimal;

public class TransactionRequest {
    private TransactionType transactionType;
    private BigDecimal amount;
    private String currency;
    private String userId;
    public TransactionType getTransactionType() {
        return transactionType;
    }
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getUserId() { return userId ;}
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public void setUserId(String userId) { this.userId = userId; }
}