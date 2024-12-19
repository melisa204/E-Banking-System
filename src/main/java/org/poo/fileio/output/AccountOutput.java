package org.poo.fileio.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.Account;
import org.poo.Card;

import java.util.ArrayList;

public class AccountOutput {
    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<CardOutput> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CardOutput> cards) {
        this.cards = cards;
    }
    @JsonProperty("IBAN")
    String IBAN;
    double balance;
    String currency;
    String type;
    ArrayList<CardOutput> cards = new ArrayList<>();

    public AccountOutput(Account account) {
        this.IBAN = account.getIban();
        this.balance = account.getBalance();
        this.currency = account.getCurrency();
        this.type = account.getAccountType();
        if (account.getCards() != null) {
            for (Card card : account.getCards()) {
                CardOutput cardOutput = new CardOutput(card);
                cards.add(cardOutput);
            }
        }
    }
}
