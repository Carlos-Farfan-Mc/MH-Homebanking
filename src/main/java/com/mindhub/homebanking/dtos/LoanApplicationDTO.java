package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private Long id;
    private double amount;
    private int payment;
    private String accountNumber;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(Long id, double amount, int payment, String accountNumber) {
        this.id = id;
        this.amount = amount;
        this.payment = payment;
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
