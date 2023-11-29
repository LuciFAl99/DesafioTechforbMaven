package com.DesafioTecnico.Techforb.DTOs;


import com.DesafioTecnico.Techforb.models.Card;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CardDTO {
    private long id;
    private String number;
    private double balance;
    private double revenues;
    private double expenditures;
    private LocalDateTime creationDate;
    private LocalDateTime thruDate;
    private List<TransactionDTO> transactions;
    public CardDTO(Card card){
        this.id = card.getId();
        this.number = card.getNumber();
        this.balance = card.getBalance();
        this.revenues = card.getRevenues();
        this.expenditures = card.getExpenditures();
        this.creationDate = card.getCreationDate();
        this.thruDate = card.getThruDate();
        this.transactions = card.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getRevenues() {
        return revenues;
    }

    public void setRevenues(double revenues) {
        this.revenues = revenues;
    }

    public double getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(double expenditures) {
        this.expenditures = expenditures;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }
}
