package org.poo.fileio.output;

public class DeleteCardOutput extends TransactionOutput {
    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private String card;
    private String cardHolder;
    private String account;
    public DeleteCardOutput(int timestamp, String description, String card, String cardHolder, String account) {
        super(timestamp, description);
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }
}
