package org.poo;

import java.util.ArrayList;

public class Account {
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    private String  iban;
    private double balance;
    private String currency;
    private String accountType;
    private double interestRate;

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    private double minBalance;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>(); // il declar aici

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    private ArrayList<Card> cards = new ArrayList<Card>();

    public Account(String currency, String accountType, double interestRate, double balance, String iban) {
        this.currency = currency;
        this.accountType = accountType;
        // verific ce tip de cont este
        if (this.accountType.equals("savings")) {
            // doar daca e de tipul asta are interestRate
            this.interestRate = interestRate;
        }
        this.balance = balance;
        this.iban = iban;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void setMinimumBalance(double minBalance) {
        this.minBalance = minBalance;
    }
}
