package com.DesafioTecnico.Techforb.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private double balance;
    private double revenues;
    private double expenditures;
    private LocalDateTime creationDate;
    private LocalDateTime thruDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;
    @OneToMany(mappedBy="card", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Card() {
    }

    public Card(String number,double balance, double revenues, double expenditures, LocalDateTime creationDate, LocalDateTime thruDate) {
        this.number = number;
        this.balance = balance;
        this.revenues = revenues;
        this.expenditures = expenditures;
        this.creationDate = creationDate;
        this.thruDate = thruDate;
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

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
    public void addTransaction(Transaction transaction){
        transaction.setCard(this);
        transactions.add(transaction);
    }
}
