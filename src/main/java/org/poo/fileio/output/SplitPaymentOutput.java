package org.poo.fileio.output;

import java.util.List;

public class SplitPaymentOutput extends TransactionOutput {
    public final String getCurrency() {
        return currency;
    }

    public final void setCurrency(final String currency) {
        this.currency = currency;
    }

    public final double getAmount() {
        return amount;
    }

    public final void setAmount(final double amount) {
        this.amount = amount;
    }

    public final List<String> getInvolvedAccounts() {
        return involvedAccounts;
    }

    public final void setInvolvedAccounts(final List<String> involvedAccounts) {
        this.involvedAccounts = involvedAccounts;
    }

    private String currency;
    private double amount;
    private List<String> involvedAccounts;

    public SplitPaymentOutput(final int timestamp, final String description,
                              final String currency, final double amount,
                              final List<String> involvedAccounts) {
        super(timestamp, description);
        this.currency = currency;
        System.out.println("afisez din splitoutpu amount ul care e ala plit ca sa vad daca e ok "
                + amount);
        this.amount = amount;
        System.out.println("afisez din splitoutpu amount ul care e ala plit ca sa vad "
                + "daca e ok cu this " + this.amount);
        this.involvedAccounts = involvedAccounts;
    }
}
