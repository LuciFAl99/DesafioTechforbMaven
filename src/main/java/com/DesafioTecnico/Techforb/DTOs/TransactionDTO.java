package com.DesafioTecnico.Techforb.DTOs;


import com.DesafioTecnico.Techforb.enums.TransactionState;
import com.DesafioTecnico.Techforb.enums.TransactionType;
import com.DesafioTecnico.Techforb.models.Transaction;

import java.time.LocalDateTime;

public class TransactionDTO {
    private long id;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private double amount;
    private String description;
    private LocalDateTime date;
    private double balanceTransaction;
    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.transactionType = transaction.getTransactionType();
        this.transactionState = transaction.getTransactionState();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.balanceTransaction = transaction.getBalanceTransaction();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionState getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(TransactionState transactionState) {
        this.transactionState = transactionState;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getBalanceTransaction() {
        return balanceTransaction;
    }

    public void setBalanceTransaction(double balanceTransaction) {
        this.balanceTransaction = balanceTransaction;
    }
}

