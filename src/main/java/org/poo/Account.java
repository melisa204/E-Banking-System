package org.poo;

import org.poo.utils.Admin;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    private String alias;

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

    public boolean sendMoney(Account receiver, double amount, Admin admin) {
        // verific daca au aceeasi moneda
        if (this.getCurrency().equals(receiver.getCurrency())) {
//            System.out.println("au aceeasi moneda deci doar transfer");

            if (this.getBalance() < amount) {
                System.out.println("Insufficient funds");
                return false;
            }

            // acum verific minimul de balanta
            if (this.getBalance() - amount < this.getMinBalance()) {
                System.out.println("nu ma lasa minuminul de balanta");
                return false;
            }

            // am verificat si totul e ok -> fac tfransferul
//            System.out.println("transfer " + amount + " din contul care are " + this.getBalance() + " in contul care are " + receiver.getBalance());
            this.balance -= amount;
            receiver.balance += amount;

//            System.out.println("dupa transfer: " + this.getBalance() + "  " + receiver.getBalance());

            return true;
        }

        // au currency diferit, deci trebuie sa fac conversie
//        System.out.println("au currency diferit, deci trebuie sa fac conversie");

        double finalRate = admin.getExchangeRateFromTo(currency, receiver.currency);

//        System.out.println("final rate: " + finalRate);

        if (finalRate == 0) {
            System.out.println("nu am gasit exchange rate");
            return false;
        }

        // ca sa stiu cum sa realizez calculul, decid ce tip de rata am

        double amountConverted = amount * finalRate;


//        System.out.println("amount converted: " + amountConverted);

        if (amountConverted > balance) { // TODO poate ar trb sa verific si minBalance
            System.out.println("vreau sa platesc " + amountConverted + " dar nu am destui bani in cont: " + balance);
            return false;
        }

        // am verificat si totul e ok -> fac tfransferul
//        System.out.println("transfer " + amountConverted + " din contul care are " + this.balance + " in contul care are " + receiver.balance);
        this.balance -= amount;
        receiver.balance += amountConverted;

//        System.out.println("dupa transfer: " + this.balance + "  " + receiver.balance);

        return true;
    }
}
