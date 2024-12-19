package org.poo;

import org.poo.utils.Admin;
import org.poo.utils.Utils;

public class Card { // TODO: nu uita ca dupa ce se face plata cu one time, se genereaza altul!!! si el devine blocat
    boolean oneTimeCard; // il pun by default pe false
    boolean frozen = false; // il pun by default pe false
    String cardNumber;
    Admin admin;

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

    public Card generateNewCard(boolean oneTime) {
        String cardNumber = Utils.generateCardNumber();
        return new Card(cardNumber, oneTime);
    }

    public boolean payOnline(double amount, String currency, String description, String commerciant, Account account, Admin admin) {
        if (frozen) {
            return false;
        }

        if (oneTimeCard) {
            // inghet cardul actual
            frozen = true;
            // generez altul
            Card card = generateNewCard(true);

            // adaug cardul la lista de carduri a contului
            account.addCard(card);
        }

        ExchangeRate exchangeRate = admin.getExchangeRateFromTo(account.getCurrency(), currency);

        if (exchangeRate == null) {
            // inseamna ca nu trb sa fac conversie
            if (amount > account.getBalance()) {
                // nu am destui bani
                return false;
            }

            if (amount > account.getBalance() - account.getMinBalance()) {
                // nu am destui bani
                System.out.println("Ceva gresit cu minBalance ----  verifica");
            }

            // altfel platesc ------- poate ar trb sa verifis si minBalance!!!!!
            account.setBalance(account.getBalance() - amount);
            return true;
        }

        double amountConverted = exchangeRate.convert(amount);
        if (amountConverted > account.getBalance()) { // TODO poate ar trb sa verific si minBalance
            return false;
        }

        // altfel inseamna ca e ok daca nu trb sa mai verific si minBalance!!!!
        account.setBalance(account.getBalance() - amountConverted);
        return true;
    }
}
