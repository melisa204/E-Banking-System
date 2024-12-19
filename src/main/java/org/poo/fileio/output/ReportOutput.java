package org.poo.fileio.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ReportOutput {
    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<TransactionOutput> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionOutput> transactions) {
        this.transactions = transactions;
    }
    @JsonProperty("IBAN")
    private String IBAN;
    private double balance;
    private String currency;
    private ArrayList<TransactionOutput> transactions;

    public ReportOutput(String IBAN, double balance, String currency, ArrayList<TransactionOutput> transactions) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = currency;
        this.transactions = transactions;
    }
}
