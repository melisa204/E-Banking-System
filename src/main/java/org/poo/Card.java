package org.poo;

import org.poo.fileio.output.PayOnlineOutput;
import org.poo.fileio.output.TransactionOutput;
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

    public boolean payOnline(double amount, String currency, String description, String commerciant, Account account, Admin admin, int timestamp) {
        if (frozen) {
            TransactionOutput transaction = new TransactionOutput(timestamp, "The card is frozen");

            // adaug tranzactia la lista
            account.addTransaction(transaction);
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

        if (currency.equals(account.getCurrency())) {
            // inseamna ca nu trb sa fac conversie
            if (amount > account.getBalance()) {
                // nu am destui bani
                System.out.println(cardNumber + " nu are destui bani");

                TransactionOutput transaction = new TransactionOutput(timestamp, "Insufficient funds");

                // adaug tranzactia in lista de tranzactii a contului
                account.addTransaction(transaction);
                return false;
            }

            if (amount > account.getBalance() - account.getMinBalance()) { // TODO nu stiu daca trb sa verific sau nu
                // nu am destui bani
                System.out.println("Ceva gresit cu minBalance ----  verifica");
                TransactionOutput transaction = new TransactionOutput(timestamp, "The card is frozen");

                // adaug tranzactia la lista
                account.addTransaction(transaction);

                // inghet cardul
                frozen = true;
                status = "frozen";

                return false;
            }

            // altfel platesc ------- poate ar trb sa verific si minBalance!!!!!
            account.setBalance(account.getBalance() - amount);

            // creez tranzactia
            System.out.println("VALOAREA PE CARE O PUN IN PAYMENTUL DE LA CARD ESTE CEA PE CARE N AM MODIFICAT ");
            PayOnlineOutput transaction = new PayOnlineOutput(timestamp, "Card payment", amount, commerciant);

//        // adaug tranzactia in lista de tranzactii a contului
            account.addTransaction(transaction);
            return true;
        }
        // altfel trb sa convertesc
        System.out.println("from este " + account.getCurrency() + " iar to este " + currency);
        double finalRate = admin.getExchangeRateFromTo(currency, account.getCurrency());

        System.out.println("rata finala e " + finalRate);

        if (finalRate == 0) {
            // inseamna ca n am gasit si totusi ar fi trb
            System.out.println("nu am gaist exchange desi ar fi trb");
            return false;
        }

        double amountConverted = amount * finalRate;

        System.out.println("in final o sa platesc " + amountConverted + " in loc de " + amount);
        System.out.println("iar in cont am " + account.getBalance());

        if (amountConverted > account.getBalance()) { // TODO poate ar trb sa verific si minBalance
            System.out.println("vreau sa platesc " + amountConverted + " dar nu am destui bani in cont: " + account.getBalance());
            TransactionOutput transaction = new TransactionOutput(timestamp, "Insufficient funds");

            // adaug tranzactia in lista de tranzactii a contului
            account.addTransaction(transaction);
            return false;
        }

        System.out.println("platesc " + amountConverted + " in loc de " + amount + " la comerciantul " + commerciant);
        // altfel inseamna ca e ok daca nu trb sa mai verific si minBalance!!!!
        account.setBalance(account.getBalance() - amountConverted);

        // creez tranzactia
        PayOnlineOutput transaction = new PayOnlineOutput(timestamp, "Card payment", amountConverted, commerciant);

        // adaug tranzactia in lista de tranzactii a contului
        account.addTransaction(transaction);


        return true;
    }
}
