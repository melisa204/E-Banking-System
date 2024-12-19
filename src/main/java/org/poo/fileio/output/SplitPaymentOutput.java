package org.poo.fileio.output;

import org.poo.transactions.SplitPayment;

import java.util.List;

public class SplitPaymentOutput extends TransactionOutput {
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
    }

    public void setInvolvedAccounts(List<String> involvedAccounts) {
        this.involvedAccounts = involvedAccounts;
    }

    private String currency;
    private double amount;
    private List<String> involvedAccounts;

    public SplitPaymentOutput(int timestamp, String description, String currency, double amount, List<String> involvedAccounts) {
        super(timestamp, description);
        this.currency = currency;
        System.out.println("afisez din splitoutpu amount ul care e ala plit ca sa vad daca e ok " + amount);
        this.amount = amount;
        System.out.println("afisez din splitoutpu amount ul care e ala plit ca sa vad daca e ok cu this " + this.amount);
        this.involvedAccounts = involvedAccounts;
    }
}
