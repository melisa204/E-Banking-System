package org.poo;

import org.poo.fileio.input.UserInput;

import java.util.ArrayList;

public class User {
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String firstName;
    private String lastName;
    private  String email;
    private ArrayList<Account> accounts = new ArrayList<>();

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    public User(UserInput user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

    public void addNewAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean hasAccount(Account account) {
        for (Account acc : accounts) {
            if (acc.getIban().equals(account.getIban())) {
                return true;
            }
        }
        return false;
    }
}
