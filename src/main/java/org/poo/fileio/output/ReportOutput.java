package org.poo.fileio.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ReportOutput {
    public final void setIBAN(final String IBAN) {
        this.IBAN = IBAN;
    }

    public final double getBalance() {
        return balance;
    }

    public final void setBalance(final double balance) {
        this.balance = balance;
    }

    public final String getCurrency() {
        return currency;
    }

    public final void setCurrency(final String currency) {
        this.currency = currency;
    }

    public final ArrayList<TransactionOutput> getTransactions() {
        return transactions;
    }

    public final void setTransactions(final ArrayList<TransactionOutput> transactions) {
        this.transactions = transactions;
    }
    @JsonProperty("IBAN")
    private String IBAN;
    private double balance;
    private String currency;
    private ArrayList<TransactionOutput> transactions;

    public ReportOutput(final String IBAN, final double balance, final String currency,
                        final ArrayList<TransactionOutput> transactions) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = currency;
        this.transactions = transactions;
    }
}
