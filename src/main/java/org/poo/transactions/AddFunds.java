package org.poo.transactions;

import org.poo.Account;
import org.poo.fileio.input.CommandInput;
import org.poo.utils.Admin;

public class AddFunds extends BaseCommand{
    double amount;
    Admin admin;
    String account;
    public AddFunds(String command, int timestamp, String account, double amount, Admin admin) {
        super(command, timestamp);
        this.account = account;
        this.amount = amount;
        this.admin = admin;
    }
    @Override
    public void execute() {
//        admin = Admin.getInstance();

        Account currentAccount = admin.getAccountByIban(account);
        if (currentAccount == null) {
            System.out.println("Account not found from addFunds in execute!!!!!!! -> mesaj de la mine");
            return;
        }

        currentAccount.setBalance(currentAccount.getBalance() + amount);
        // TODO grija ca s ar putea sa dea null si sa trb sa verific?
    }
}
