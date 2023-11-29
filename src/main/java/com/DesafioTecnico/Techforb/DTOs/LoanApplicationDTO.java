package com.DesafioTecnico.Techforb.DTOs;

public class LoanApplicationDTO {
    private long  loanId;
    private double amount;
    private int payments;
    private String destinationCardNumber;


    public LoanApplicationDTO(long loanId, double amount, int payments, String destinationCardNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.destinationCardNumber = destinationCardNumber;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getDestinationCardNumber() {
        return destinationCardNumber;
    }

    public void setDestinationCardNumber(String destinationCardNumber) {
        this.destinationCardNumber = destinationCardNumber;
    }

}
