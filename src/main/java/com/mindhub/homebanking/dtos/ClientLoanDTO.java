package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.Set;

public class ClientLoanDTO {
    private Long id;
    private Long idLoan;
    private String nameLoan;
    private double amount;
    private int payments;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.idLoan = clientLoan.getLoan().getId();
        this.nameLoan = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(Long idLoan) {
        this.idLoan = idLoan;
    }

    public String getNameLoan() {
        return nameLoan;
    }

    public void setNameLoan(String nameLoan) {
        this.nameLoan = nameLoan;
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

}
