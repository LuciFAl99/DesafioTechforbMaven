package com.DesafioTecnico.Techforb.models;

import com.DesafioTecnico.Techforb.enums.TransactionState;
import com.DesafioTecnico.Techforb.enums.TransactionType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private TransactionType transactionType;
    private TransactionState transactionState;
    private double amount;
    private String description;
    private LocalDateTime date;
    private double balanceTransaction;
    @ManyToOne(fetch = FetchType.EAGER)
    private Card card;

    public Transaction() {
    }

    public Transaction(TransactionType transactionType, TransactionState transactionState, double amount, String description, LocalDateTime date, double balanceTransaction) {
        this.transactionType = transactionType;
        this.transactionState = transactionState;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.balanceTransaction = balanceTransaction;
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

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
