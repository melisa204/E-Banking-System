package org.poo.transactions;

import org.poo.Account;
import org.poo.Card;
import org.poo.Transaction;
import org.poo.User;
import org.poo.utils.Admin;

import javax.xml.crypto.dsig.TransformService;

public class PayOnline extends BaseCommand {
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommerciant() {
        return commerciant;
    }

    public void setCommerciant(String commerciant) {
        this.commerciant = commerciant;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String cardNumber;
    private double amount;
    private String currency;
    private String description;
    private String commerciant;
    Admin admin;
    private User user;

    public PayOnline(User user, String command, int timestamp, String cardNumber, double amount, String currency, String description, String commerciant, Admin admin) {
        super(command, timestamp);
        this.user = user;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.commerciant = commerciant;
        this.admin = admin;
    }

    public void execute() {
        // verific daca cardul apartine userului
        Card card = admin.getCardByNumber(cardNumber, user);
        Account account = admin.getAccountByCardNumber(cardNumber, user);

        if (card == null) { // vad cum adaug in tranzactii!!!!!!!!!!!!
            System.out.println("Cardul nu exista!");
            return;
        }

        // efectuez plata
        boolean paid = card.payOnline(amount, currency, description, commerciant, account, admin);

        // creez tranzactia
        Transaction transaction = new Transaction("Online payment", getTimestamp());

        // adaug tranzactia in lista de tranzactii a contului
        account.addTransaction(transaction);
    }
}
