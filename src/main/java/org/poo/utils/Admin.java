package org.poo.utils;

import org.poo.Account;
import org.poo.Card;
import org.poo.ExchangeRate;
import org.poo.User;
import org.poo.transactions.Command;

import java.util.ArrayList;
import java.util.Iterator;

public final class Admin {
    private static Admin instance = null;
    private Admin() { }
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }


    public void setExchangeRates(ArrayList<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    private ArrayList<User> users = new ArrayList<>(); // aici am toti userii
    private ArrayList<ExchangeRate> exchangeRates = new ArrayList<>(); // aici am toate ratele de schimb
    private ArrayList<Command> commands = new ArrayList<>(); // aici am toate comenzile

    // identific un user dupa email
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) { // sau cu egal??
                return user;
            }
        }
        return null;
    }
    public void addUser(User user) {
        users.add(user);
    }
    public void addExchangeRate(ExchangeRate exchangeRate) {
        exchangeRates.add(exchangeRate);
    }
    public void addCommand(Command command) {
        commands.add(command);
    }

    public Account getAccountByIban(String iban) {
        System.out.println("Caut in lista de useri contul cu iban: " + iban);
        for (User user : users) {
            if (user.getAccounts() == null) {
                return null;
            }
            for (Account account : user.getAccounts()) {
                System.out.println("user " + user.getFirstName() + " iban: " + account.getIban());
                if (account.getIban().equals(iban)) {
                    return account;
                }
            }
        }
        return null;
    }

    public User getUserByAccount(Account account) {
        for (User user : users) {
            if (user.getAccounts() == null) {
                return null;
            }
            for (Account acc : user.getAccounts()) {
                if (acc.getIban().equals(account.getIban())) {
                    return user;
                }
            }
        }
        return null;
    }

    public void removeAccount(Account account) {
        for (User user : users) {
            if (user.getAccounts() == null) {
                return;
            }
            for (Account acc : user.getAccounts()) {
                if (acc.getIban().equals(account.getIban())) {
                    // mai intai sterg cardurile atasate contului
                    for (int i = 0; i < acc.getCards().size(); i++) {
                        acc.getCards().remove(i);
                    }
                    // sterg si tranzaactiile contului
                    for (int i = 0; i < acc.getTransactions().size(); i++) {
                        acc.getTransactions().remove(i);
                    }
                    user.getAccounts().remove(acc);
                    return;
                }
            }
        }

//        // sau daca imi da gresit pot sa folosesc varianta asta
//        Iterator<Account> iterator = user.getAccounts().iterator();
//        while (iterator.hasNext()) {
//            Account acc = iterator.next();
//            if (acc.getIban().equals(account.getIban())) {
//                // Șterg cardurile și tranzacțiile
//                acc.getCards().clear();
//                acc.getTransactions().clear();
//
//                // Șterg contul folosind iterator
//                iterator.remove();
//                return;
//            }
//        }
    }

    public void clear() {
        System.out.println("Admin will clear: users=" + users.size() + ", exchangeRates=" + exchangeRates.size() + ", commands=" + commands.size());
        users.clear();
        exchangeRates.clear();
        commands.clear();
    }

    public static void resetInstance() {
        instance = null;
    }

    public Card getCardByNumber(String cardNumber, User user) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
                }
            }
        return null;
    }

    public boolean deleteCard(String cardNumber, User user) {
        for (Account account : user.getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    account.getCards().remove(card);
                    return true;
                }
            }
        }
        return false;
    }

    public Account getAccountByCardNumber(String cardNumber, User user) {
        for (Account account : user.getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return account;
                }
            }
        }
        return null;
    }

    public Card getCardOnlyByNumber(String cardNumber) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
                }
            }
        }
        return null;
    }

    public double getExchangeRateFromTo(String from, String to) {
        // cuat rata directa
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(from) && exchangeRate.getTo().equals(to)) {
                return exchangeRate.getRate();
            }
        }

        // caut rata inversa
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(to) && exchangeRate.getTo().equals(from)) {
                return 1 / exchangeRate.getRate();
            }
        }

        // caut succesiv, daca sunt din una in alta
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(from)) {
                double intermediateRate = getExchangeRateFromTo(exchangeRate.getTo(), to);
                if (intermediateRate != 0) {
                    System.out.println("*******IN ADMIN Am găsit rata de schimb de la " + from + " la " + to + " prin " + exchangeRate.getTo());
                    return exchangeRate.getRate() * intermediateRate;
                }
            }
        }

        // daca nu gasesc rata
        return 0;
    }

    public boolean isDirectRate(String from, String to) {
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(from) && exchangeRate.getTo().equals(to)) {
                return true;
            }
        }
        return false;
    }

    public Account getAccountByAlias(String alias) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                if (account.getAlias().equals(alias)) {
                    return account;
                }
            }
        }
        return null;
    }
}
