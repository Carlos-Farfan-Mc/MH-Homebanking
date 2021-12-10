package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private double maxAmount;
    private double percentage;

    @ElementCollection
    @Column(name = "payment")
    List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, double maxAmount, double percentage, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.percentage = percentage;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public List<Client> getClients() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }
}