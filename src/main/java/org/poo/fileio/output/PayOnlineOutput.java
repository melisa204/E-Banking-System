package org.poo.fileio.output;

public class PayOnlineOutput extends TransactionOutput {
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCommerciant() {
        return commerciant;
    }

    public void setCommerciant(String commerciant) {
        this.commerciant = commerciant;
    }

    double amount;
    String commerciant;
    public PayOnlineOutput(int timestamp, String description, double amount, String commerciant) {
        super(timestamp, description);
        this.amount = amount;
        this.commerciant = commerciant;
    }
}
