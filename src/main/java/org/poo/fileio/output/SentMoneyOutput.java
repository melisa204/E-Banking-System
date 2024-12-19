package org.poo.fileio.output;

public class SentMoneyOutput extends TransactionOutput {
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    private String amount;
    private String transferType;
    private String senderIBAN;

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public void setSenderIBAN(String senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public void setReceiverIBAN(String receiverIBAN) {
        this.receiverIBAN = receiverIBAN;
    }

    private String receiverIBAN;

    public SentMoneyOutput(int timestamp, String description, String amount, String transferType, String senderIBAN, String receiverIBAN) {
        super(timestamp, description);
        this.amount = amount;
        this.transferType = transferType;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
    }

}
