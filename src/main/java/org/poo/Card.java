package org.poo;

public class Card { // TODO: nu uita ca dupa ce se face plata cu one time, se genereaza altul!!! si el devine blocat
    boolean oneTimeCard; // il pun by default pe false
    boolean frozen = false; // il pun by default pe false
    String cardNumber;

    public boolean isOneTimeCard() {
        return oneTimeCard;
    }

    public void setOneTimeCard(boolean oneTimeCard) {
        this.oneTimeCard = oneTimeCard;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

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

    String status = "active"; // il pun by default pe active

    public Card (String cardNumber, boolean oneTimeCard) {
        this.cardNumber = cardNumber;
        this.oneTimeCard = oneTimeCard;
    }
}
