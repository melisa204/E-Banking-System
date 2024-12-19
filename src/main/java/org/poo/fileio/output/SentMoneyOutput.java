package org.poo.fileio.output;

public class SentMoneyOutput extends TransactionOutput {
    public final String getAmount() {
        return amount;
    }

    public final void setAmount(final String amount) {
        this.amount = amount;
    }

    public final String getTransferType() {
        return transferType;
    }

    public final void setTransferType(final String transferType) {
        this.transferType = transferType;
    }

    private String amount;
    private String transferType;
    private String senderIBAN;

    public final String getSenderIBAN() {
        return senderIBAN;
    }

    public final void setSenderIBAN(final String senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    public final String getReceiverIBAN() {
        return receiverIBAN;
    }

    public final void setReceiverIBAN(final String receiverIBAN) {
        this.receiverIBAN = receiverIBAN;
    }

    private String receiverIBAN;

    public SentMoneyOutput(final int timestamp, final String description, final String amount,
                           final String transferType,
                           final String senderIBAN, final String receiverIBAN) {
        super(timestamp, description);
        this.amount = amount;
        this.transferType = transferType;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
    }

}
