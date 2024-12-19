package org.poo.utils;

import org.poo.Account;
import org.poo.Card;
import org.poo.ExchangeRate;
import org.poo.User;
import org.poo.transactions.Command;

import java.util.*;

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

    public void addInverseRates() {
        int size = exchangeRates.size();

        for (int i = 0; i < size; i++) {
            ExchangeRate original = exchangeRates.get(i);

            // Creăm rata inversă
            ExchangeRate inverse = new ExchangeRate(original.getTo(), original.getFrom(), 1 / original.getRate());

            System.out.println("Adaug rata inversa: " + inverse.getFrom() + " " + inverse.getTo() + " " + inverse.getRate());

            // Adăugăm rata inversă în listă
            exchangeRates.add(inverse);
        }
    }

    public double getExchangeRateFromTo(String from, String to) {
        // Caut rata directă
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(from) && exchangeRate.getTo().equals(to)) {
                System.out.println("Rata directa: " + exchangeRate.getFrom() + " " + exchangeRate.getTo() + " " + exchangeRate.getRate());
                return exchangeRate.getRate(); // Rată directă găsită
            }
        }

        // Caut rate intermediare recursiv
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(from)) {
                double intermediateRate = getExchangeRateFromTo(exchangeRate.getTo(), to);
                if (intermediateRate > 0) { // Dacă există o cale validă
                    System.out.println("Rata intermediara: " + exchangeRate.getFrom() + " " + exchangeRate.getTo() + " " + exchangeRate.getRate());
                    return exchangeRate.getRate() * intermediateRate; // Înmulțim ratele
                }
            }
        }

        return 0; // Dacă nu există cale
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
                // prima data verific sa nu fie null
                if (account.getAlias() == null) {
                    continue;
                }
                if (account.getAlias().equals(alias)) {
                    return account;
                }
            }
        }
        return null;
    }

    public Account getAccountOnlyByCardNumber(String iban) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(iban)) {
                        return account;
                    }
                }
            }
        }
        return null;
    }
}
