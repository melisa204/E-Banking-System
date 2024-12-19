package org.poo.transactions.card;

import org.poo.Account;
import org.poo.User;
import org.poo.transactions.BaseCommand;
import org.poo.utils.Admin;

public class DeleteCard extends BaseCommand {
    String cardNumber;
    User user;
    Admin admin;

    public DeleteCard(User user, String command, int timestamp, String cardNumber, Admin admin) {
        super(command, timestamp);
        this.user = user;
        this.cardNumber = cardNumber;
        this.admin = admin;
    }

    public void execute() {
        // salvez contul curent ca sa adaug la lista lui de tranzactii
        Account currentAccount = admin.getAccountByCardNumber(cardNumber, user);

        // sterg cardul
        boolean done = admin.deleteCard(cardNumber, user); // asta nu cred ca are output

        if (done == false) { // asta nu cred ca are output
            System.out.println("Card not found ------ adaugat de mineeeee");
            return;
        }

//        // creez tranzactia specifica
//        Transaction transaction = new Transaction("Card deleted", getTimestamp());
//
//        // adaug tranzactia in lista de tranzactii a contului
//        currentAccount.addTransaction(transaction);
    }
}
