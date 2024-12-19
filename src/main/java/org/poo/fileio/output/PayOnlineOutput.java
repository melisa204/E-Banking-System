package org.poo.fileio.output;

public class PayOnlineOutput extends TransactionOutput {
    public final double getAmount() {
        return amount;
    }

    public final void setAmount(final double amount) {
        this.amount = amount;
    }

    public final String getCommerciant() {
        return commerciant;
    }

    public final void setCommerciant(final String commerciant) {
        this.commerciant = commerciant;
    }

    private double amount;
    private String commerciant;
    public PayOnlineOutput(final int timestamp, final String description,
                           final double amount, final String commerciant) {
        super(timestamp, description);
        this.setAmount(amount);
        this.setCommerciant(commerciant);
    }
}
