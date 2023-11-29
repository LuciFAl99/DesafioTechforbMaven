package com.DesafioTecnico.Techforb.DTOs;


import com.DesafioTecnico.Techforb.models.ClientLoan;

import java.time.LocalDateTime;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private String name;
    private double amount;
    private double finalAmount;
    private int payments;
    private double monthly;
    private double interest;
    private int originalPayments;
    private LocalDateTime date;
    public ClientLoanDTO (ClientLoan clientLoan){
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.finalAmount = clientLoan.getFinalAmount();
        this.payments = clientLoan.getPayments();
        this.monthly = (clientLoan.getFinalAmount() / clientLoan.getOriginalPayments());
        this.interest = clientLoan.getLoan().getInterest();
        this.originalPayments = clientLoan.getOriginalPayments();
        this.date = clientLoan.getDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public double getMonthly() {
        return monthly;
    }

    public void setMonthly(double monthly) {
        this.monthly = monthly;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getOriginalPayments() {
        return originalPayments;
    }

    public void setOriginalPayments(int originalPayments) {
        this.originalPayments = originalPayments;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
