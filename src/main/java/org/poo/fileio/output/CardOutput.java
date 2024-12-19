package org.poo.fileio.output;

import org.poo.Card;

public class CardOutput {
    public final String getCardNumber() {
        return cardNumber;
    }

    public final void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public final String getStatus() {
        return status;
    }

    public final void setStatus(final String status) {
        this.status = status;
    }

    private String cardNumber;
    private String status;

    public CardOutput(final Card card) {
        this.setCardNumber(card.getCardNumber());
        this.setStatus(card.getStatus());
    }
}
