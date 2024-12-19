package org.poo.fileio.output;

public class DeleteCardOutput extends TransactionOutput {
    public final String getCard() {
        return card;
    }

    public final void setCard(final String card) {
        this.card = card;
    }

    public final String getCardHolder() {
        return cardHolder;
    }

    public final void setCardHolder(final String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public final String getAccount() {
        return account;
    }

    public final void setAccount(final String account) {
        this.account = account;
    }

    private String card;
    private String cardHolder;
    private String account;
    public DeleteCardOutput(final int timestamp, final String description, final String card,
                            final String cardHolder, final String account) {
        super(timestamp, description);
        this.card = card;
        this.cardHolder = cardHolder;
        this.account = account;
    }
}
