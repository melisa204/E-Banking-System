package org.poo.fileio.output;

import org.poo.Card;

public class CardOutput {
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String cardNumber;
    String status;

    public CardOutput(Card card) {
        this.cardNumber = card.getCardNumber();
        this.status = card.getStatus();
    }
}
