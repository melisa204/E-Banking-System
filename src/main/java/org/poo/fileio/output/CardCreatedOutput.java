package org.poo.fileio.output;

public class CardCreatedOutput extends TransactionOutput {
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
    public CardCreatedOutput(final int timestamp, final String description,
                             final String card, final String cardHolder,
                             final String account) {
        super(timestamp, description);
        this.setCard(card);
        this.setCardHolder(cardHolder);
        this.setAccount(account);
    }
}
