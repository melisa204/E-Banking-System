package org.poo.transactions.card;

import org.poo.Account;
import org.poo.User;
import org.poo.fileio.output.DeleteCardOutput;
import org.poo.commands.BaseCommand;
import org.poo.utils.Admin;

public class DeleteCard extends BaseCommand {
    private String cardNumber;
    private User user;
    private Admin admin;

    public DeleteCard(final User user, final String command, final int timestamp,
                      final String cardNumber, final Admin admin) {
        super(command, timestamp);
        this.setUser(user);
        this.setCardNumber(cardNumber);
        this.setAdmin(admin);
    }
    /**
     * Execută logica pentru comanda de ștergere a unui card asociat unui cont.
     *
     * Funcționare:
     * - Găsește contul asociat cardului utilizând metoda `getAccountByCardNumber` din `Admin`.
     * - Șterge cardul specificat utilizând metoda `deleteCard` din `Admin`.
     *   - Dacă ștergerea nu reușește (cardul nu există sau nu poate fi eliminat), execuția este
     *   întreruptă.
     * - Creează o tranzacție care marchează ștergerea cardului, incluzând detalii precum:
     *   - Timestamp-ul comenzii.
     *   - Numărul cardului șters.
     *   - Email-ul utilizatorului.
     *   - IBAN-ul contului asociat cardului.
     * - Adaugă tranzacția generată în istoricul tranzacțiilor contului.
     *
     * Scop:
     * - Permite utilizatorului să elimine un card asociat unui cont, marcând acțiunea printr-o
     * tranzacție.
     *
     * Detalii:
     * - Dacă cardul nu poate fi găsit sau șters, metoda nu efectuează alte acțiuni.
     * - Tranzacția generată reflectă eliminarea cardului și este salvată în istoricul contului
     * asociat.
     */

    public void execute() {
        // salvez contul curent ca sa adaug la lista lui de tranzactii
        Account currentAccount = getAdmin().getAccountByCardNumber(getCardNumber(), getUser());

        // sterg cardul
        boolean done = getAdmin().deleteCard(getCardNumber(), getUser());

        if (!done) {
            return;
        }

        // creez tranzactia specifica
        DeleteCardOutput transaction = new DeleteCardOutput(getTimestamp(),
                "The card has been destroyed", getCardNumber(), getUser().getEmail(),
                currentAccount.getIban());

        // adaug tranzactia in lista de tranzactii a contului
        currentAccount.addTransaction(transaction);
    }

    public final String getCardNumber() {
        return cardNumber;
    }

    public final void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public final User getUser() {
        return user;
    }

    public final void setUser(final User user) {
        this.user = user;
    }

    public final Admin getAdmin() {
        return admin;
    }

    public final void setAdmin(final Admin admin) {
        this.admin = admin;
    }
}
