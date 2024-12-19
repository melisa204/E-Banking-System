package org.poo.transactions;

import org.poo.Account;
import org.poo.Transaction;
import org.poo.utils.Admin;

public class SetMinimumBalance extends BaseCommand { // TODO s ar putea sa am nevoie de output pentru eroare?!?!?!?
    String account;
    double minimumBalance;
    Admin admin;
    public SetMinimumBalance(String command, int timestamp, String account, double minimumBalance, Admin admin) {
        super(command, timestamp);
        this.account = account;
        this.minimumBalance = minimumBalance;
        this.admin = admin;
    }

    public void execute() { // TODO cred?? ca trb si tranzactie
        // caut contul
        Account currentAccount = admin.getAccountByIban(account); // TODO ar trb sa verific ca exista?

        if (currentAccount == null) {
            System.out.println("Account not found pentru minBalance------ adaugat de mineeeee");
            return;
        }

        currentAccount.setMinimumBalance(minimumBalance);

        // creez transaction
        Transaction transaction = new Transaction("Minimum balance set to " + minimumBalance, getTimestamp());

        // adaug tranzactia in lista de tranzactii a contului
        currentAccount.addTransaction(transaction);
    }

}
