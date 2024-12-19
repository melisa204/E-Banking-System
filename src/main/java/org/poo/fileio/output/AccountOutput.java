package org.poo.fileio.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.poo.Account;
import org.poo.Card;

import java.util.ArrayList;

public class AccountOutput {
    public final void setIBAN(final String IBAN) {
        this.IBAN = IBAN;
    }

    public final double getBalance() {
        return balance;
    }

    public final void setBalance(final double balance) {
        this.balance = balance;
    }

    public final String getCurrency() {
        return currency;
    }

    public final void setCurrency(final String currency) {
        this.currency = currency;
    }

    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final ArrayList<CardOutput> getCards() {
        return cards;
    }

    public final void setCards(final ArrayList<CardOutput> cards) {
        this.cards = cards;
    }
    @JsonProperty("IBAN")
    String IBAN;
    private double balance;
    private String currency;
    private String type;
    private ArrayList<CardOutput> cards = new ArrayList<>();

    public AccountOutput(final Account account) {
        this.IBAN = account.getIban();
        this.setBalance(account.getBalance());
        this.setCurrency(account.getCurrency());
        this.setType(account.getAccountType());
        if (account.getCards() != null) {
            for (Card card : account.getCards()) {
                CardOutput cardOutput = new CardOutput(card);
                getCards().add(cardOutput);
            }
        }
    }
}
