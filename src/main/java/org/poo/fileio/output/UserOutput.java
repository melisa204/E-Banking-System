package org.poo.fileio.output;

import org.poo.Account;
import org.poo.User;

import java.util.ArrayList;

public class UserOutput {
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

    public ArrayList<AccountOutput> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<AccountOutput> accounts) {
        this.accounts = accounts;
    }

    String firstName;
    String lastName;
    String email;
    ArrayList<AccountOutput> accounts = new ArrayList<AccountOutput>();
    public UserOutput(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        for (Account account : user.getAccounts()) {
            AccountOutput accountOutput = new AccountOutput(account);
            accounts.add(accountOutput);
        }
    }
}
