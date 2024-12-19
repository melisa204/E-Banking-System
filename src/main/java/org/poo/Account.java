package org.poo;

import org.poo.fileio.output.SplitPaymentOutput;
import org.poo.fileio.output.TransactionOutput;
import org.poo.utils.Admin;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<TransactionOutput> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionOutput> transactions) {
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
    private ArrayList<TransactionOutput> transactions = new ArrayList<TransactionOutput>(); // il declar aici

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

    public void addTransaction(TransactionOutput transaction) {
        this.transactions.add(transaction);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void setMinimumBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public boolean sendMoney(Account receiver, double amount, Admin admin, int timestamp) {
        // verific daca au aceeasi moneda
        if (this.getCurrency().equals(receiver.getCurrency())) {
            System.out.println("au aceeasi moneda deci doar transfer");

            if (this.getBalance() < amount) {
                TransactionOutput transaction = new TransactionOutput(timestamp, "Insufficient funds");

                // adaug tranzactia in lista de tranzactii a contului
                this.addTransaction(transaction);
                System.out.println("Insufficient funds");
                return false;
            }

            // acum verific minimul de balanta
//            if (this.getBalance() - amount < this.getMinBalance()) {
//                System.out.println("nu ma lasa minuminul de balanta");
//                return false;
//            }

            // am verificat si totul e ok -> fac tfransferul
            System.out.println("transfer " + amount + " din contul care are " + this.getBalance() + " in contul care are " + receiver.getBalance());
            this.balance -= amount;
            receiver.balance += amount;

            System.out.println("dupa transfer: " + this.getBalance() + "  " + receiver.getBalance());

            return true;
        }

        // au currency diferit, deci trebuie sa fac conversie
        System.out.println("au currency diferit, deci trebuie sa fac conversie");

        double finalRate = admin.getExchangeRateFromTo(currency, receiver.currency);

        System.out.println("final rate: " + finalRate);

        if (finalRate == 0) {
            System.out.println("nu am gasit exchange rate");
            return false;
        }

        // ca sa stiu cum sa realizez calculul, decid ce tip de rata am

        double amountConverted = amount * finalRate;


//        System.out.println("amount converted: " + amountConverted);

        if (amount > balance) { // TODO poate ar trb sa verific si minBalance
            System.out.println("vreau sa platesc " + amountConverted + " dar nu am destui bani in cont: " + balance);
            TransactionOutput transaction = new TransactionOutput(timestamp, "Insufficient funds");

            // adaug tranzactia in lista de tranzactii a contului
            this.addTransaction(transaction);
            System.out.println("Insufficient funds");

            return false;
        }

        // am verificat si totul e ok -> fac tfransferul
//        System.out.println("transfer " + amountConverted + " din contul care are " + this.balance + " in contul care are " + receiver.balance);
        this.balance -= amount;
        receiver.balance += amountConverted;

//        System.out.println("dupa transfer: " + this.balance + "  " + receiver.balance);

        return true;
    }

    public void changeInterestRate(double newInterestRate) {
        this.interestRate = newInterestRate;
    }

    public boolean checkBalance(double amount, String currency, Admin admin) {
        // verific daca e aceeasi moneda
        if (this.currency.equals(currency)) {
            System.out.println(this.iban + " are " + this.balance + this.currency + " si vrea sa plateasca " + amount + currency);
            if (this.balance < amount) {
                // inseamna ca nu am destui bani
                System.out.println(this.iban + " nu are destui bani");
                return false;
            }

            // inseamna ca am destui bani
            return true;
        }

        // inseamna ca trb sa fac transferul
        double finalRate = admin.getExchangeRateFromTo(currency, this.currency);
        System.out.println("final rate: " + finalRate);

        if (finalRate == 0) {
            return false;
        }

        double amountConverted = amount * finalRate;
        System.out.println("amount converted: " + amountConverted);

        if (this.balance < amountConverted) { // ar trb sa verific si minBalance?
            System.out.println(this.iban + " nu are destui bani dupa conversie");
            return false;
        }

        return true;
    }

    public void splitPaymentDone(double splitAmount, String currency, Admin admin, int timestamp, double amount, List<String> accounts) {
        System.out.println("VERIFI PENTRU " + this.iban + " ca sa vad daca de aici e prb");
        String message;
        // verific daca e aceeasi moneda
        if (this.currency.equals(currency)) {
            System.out.println("split payment: " + this.iban + " are " + this.balance + " plateste " + splitAmount);
            balance -= splitAmount;

            System.out.println("dupa ce a platit: " + this.balance);

            // creez mesajul
            message = String.format("Split payment of %.2f %s", amount, currency);

            System.out.println("afisez amount inainte sa trimit la splitpaymentoutput: " + splitAmount);
            // creez tranzactia
            SplitPaymentOutput transaction = new SplitPaymentOutput(timestamp, message, currency, splitAmount, accounts);

            // adaug tranzactia in lista de tranzactii a contului
            this.addTransaction(transaction);

            return;
        }

        // inseamna ca trb sa fac transferul
        System.out.println("split payment: " + this.iban + " are " + this.balance + " plateste " + splitAmount);

        double finalRate = admin.getExchangeRateFromTo(currency, this.currency);

        double amountConverted = splitAmount * finalRate;
        System.out.println("amount converted: " + amountConverted);

        balance -= amountConverted;

        System.out.println("dupa ce a platit: " + this.balance);

        // creez mesajul
        message = String.format("Split payment of %.2f %s", amount, currency);

        // creez tranzactia
        SplitPaymentOutput transaction = new SplitPaymentOutput(timestamp, message, currency, splitAmount, accounts);

        // adaug tranzactia in lista de tranzactii a contului
        this.addTransaction(transaction);
    }
}
