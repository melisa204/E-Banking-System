package org.poo.fileio.output;

import org.poo.Account;
import org.poo.User;

import java.util.ArrayList;

public class UserOutput {
    public final String getFirstName() {
        return firstName;
    }

    public final void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(final String email) {
        this.email = email;
    }

    public final ArrayList<AccountOutput> getAccounts() {
        return accounts;
    }

    public final void setAccounts(final ArrayList<AccountOutput> accounts) {
        this.accounts = accounts;
    }

    private String firstName;
    private String lastName;
    private String email;
    private ArrayList<AccountOutput> accounts = new ArrayList<AccountOutput>();
    public UserOutput(final User user) {
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        for (Account account : user.getAccounts()) {
            AccountOutput accountOutput = new AccountOutput(account);
            getAccounts().add(accountOutput);
        }
    }
}
