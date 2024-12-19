package org.poo.transactions.card;

import org.poo.Account;
import org.poo.Card;
import org.poo.User;
import org.poo.transactions.BaseCommand;
import org.poo.utils.Admin;
import org.poo.utils.Utils;

public class CreateCard extends BaseCommand {
    String account;
    User user;
    boolean oneTime;
    Admin admin;
    public CreateCard(User user, String command, int timestamp, String account, boolean oneTime, Admin admin) {
        super(command, timestamp);
        this.account = account;
        this.user = user;
        this.oneTime = oneTime;
        this.admin = admin;
    }
    public void execute() {
        Account currentAccount = admin.getAccountByIban(account);

        // prima data verific daca contul apartine utilizatorului
        if (!user.hasAccount(currentAccount)) { // daca userul nu are contul asta
            // creez tranzactia specifica
//            Transaction transaction = new Transaction("Card creation failed??????????", getTimestamp());
//            currentAccount.addTransaction(transaction);
            return; // e ok ca nu vreau sa continui mai departe
        }

        // generez numarul cardului
        String cardNumber = Utils.generateCardNumber();
        Card card = new Card(cardNumber, oneTime);

        // adaug cardul la lista de carduri a contului
        currentAccount.addCard(card);

        // TODO grija ca s ar putea sa nu mearga tranzactia

//        // creez tranzactia specifica
//        Transaction transaction = new Transaction("Card created", getTimestamp());
//
//        // adaug tranzactia in lista de tranzactii a contului
//        currentAccount.addTransaction(transaction);
    }
}
