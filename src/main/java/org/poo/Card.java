package org.poo;

import org.poo.fileio.output.PayOnlineOutput;
import org.poo.fileio.output.TransactionOutput;
import org.poo.utils.Admin;
import org.poo.utils.Utils;

import java.util.HashSet;

public class Card {
    private boolean oneTimeCard; // il pun by default pe false
    private boolean frozen = false; // il pun by default pe false
    private String cardNumber;

    public final boolean isOneTimeCard() {
        return oneTimeCard;
    }

    public final void setOneTimeCard(final boolean oneTimeCard) {
        this.oneTimeCard = oneTimeCard;
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public final void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    public final String getCardNumber() {
        return cardNumber;
    }

    public final void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public final String getStatus() {
        return status;
    }

    public final void setStatus(final String status) {
        this.status = status;
    }

    private String status = "active"; // il pun by default pe active

    public Card(final String cardNumber, final boolean oneTimeCard) {
        this.setCardNumber(cardNumber);
        this.setOneTimeCard(oneTimeCard);
    }
    /**
     * Generează un nou card.
     *
     * Funcționare:
     * - Creează un număr de card unic utilizând utilitarul `Utils.generateCardNumber()`.
     * - Creează un obiect `Card` cu numărul generat și tipul specificat (`oneTime`).
     *
     * Scop:
     * - Permite generarea unui nou card, fie de tip permanent, fie de tip one-time.
     *
     * Detalii:
     * - Utilitatea metodei constă în reutilizarea logicii de generare a cardurilor în diferite
     * scenarii.
     * - Parametrul `oneTime` indică dacă noul card este de tip one-time (`true`) sau permanent
     * (`false`).
     *
     * @param oneTime `true` dacă cardul generat este de tip one-time, `false` dacă este permanent.
     * @return Obiectul `Card` nou creat.
     */
    public Card generateNewCard(final boolean oneTime) {
        String cardNumberLocal = Utils.generateCardNumber();
        return new Card(cardNumberLocal, oneTime);
    }
    /**
     * Efectuează o plată online utilizând cardul curent.
     *
     * Funcționare:
     * - Verifică dacă cardul este înghețat (`frozen`).
     *   - Dacă este înghețat, creează o tranzacție de eroare și returnează `false`.
     * - Dacă cardul este de tip one-time, îl îngheață și generează unul nou, pe care îl adaugă
     * în cont.
     * - Verifică dacă moneda plății coincide cu moneda contului:
     *   - Dacă sunt identice:
     *     - Verifică dacă suma depășește balanța disponibilă sau minimă.
     *     - Actualizează balanța și adaugă tranzacția dacă totul este valid.
     *   - Dacă nu sunt identice:
     *     - Calculează suma convertită folosind rata de schimb.
     *     - Verifică dacă suma convertită depășește balanța disponibilă.
     *     - Actualizează balanța și adaugă tranzacția dacă totul este valid.
     *
     * Scop:
     * - Permite efectuarea plăților online, cu suport pentru conversia valutară și gestionarea
     * statusului cardului.
     *
     * Detalii:
     * - Creează tranzacții pentru fiecare eroare sau plată reușită.
     * - Inghețarea cardului este tratată în cazurile specificate.
     * - Utilizează rata de schimb pentru a calcula suma convertită, dacă este necesar.
     *
     * @param amount Suma care trebuie plătită.
     * @param currency Moneda plății.
     * @param description Descrierea tranzacției.
     * @param commerciant Comerciantul asociat tranzacției.
     * @param account Contul asociat cardului.
     * @param admin Instanța `Admin` utilizată pentru a accesa datele.
     * @param timestamp Timpul la care are loc tranzacția.
     * @return `true` dacă plata a fost efectuată cu succes, `false` în caz contrar.
     */
    public boolean payOnline(final double amount, final String currency, final String description,
                             final String commerciant,
                             final Account account, final Admin admin, final int timestamp) {
        if (isFrozen()) {
            TransactionOutput transaction = new TransactionOutput(timestamp, "The card is frozen");

            // adaug tranzactia la lista
            account.addTransaction(transaction);
            return false;
        }

        if (isOneTimeCard()) {
            // inghet cardul actual
            setFrozen(true);
            // generez altul
            Card card = generateNewCard(true);

            // adaug cardul la lista de carduri a contului
            account.addCard(card);
        }

        if (currency.equals(account.getCurrency())) {
            // inseamna ca nu trb sa fac conversie
            if (amount > account.getBalance()) {
                // nu am destui bani
                TransactionOutput transaction = new TransactionOutput(timestamp,
                        "Insufficient funds");

                // adaug tranzactia in lista de tranzactii a contului
                account.addTransaction(transaction);
                return false;
            }

            if (amount > account.getBalance() - account.getMinBalance()) {
                // nu am destui bani
                TransactionOutput transaction = new TransactionOutput(timestamp,
                        "The card is frozen");

                // adaug tranzactia la lista
                account.addTransaction(transaction);

                // inghet cardul
                setFrozen(true);
                setStatus("frozen");

                return false;
            }

            // altfel platesc ------- poate ar trb sa verific si minBalance!!!!!
            account.setBalance(account.getBalance() - amount);

            // creez tranzactia
            PayOnlineOutput transaction = new PayOnlineOutput(timestamp,
                    "Card payment", amount, commerciant);

            // adaug tranzactia in lista de tranzactii a contului
            account.addTransaction(transaction);
            return true;
        }
        // altfel trb sa convertesc
        double finalRate = admin.getExchangeRateFromTo(currency, account.getCurrency(),
                new HashSet<>());

        if (finalRate == 0) {
            // inseamna ca n am gasit si totusi ar fi trb
            return false;
        }

        double amountConverted = amount * finalRate;

        if (amountConverted > account.getBalance()) {
            TransactionOutput transaction = new TransactionOutput(timestamp, "Insufficient funds");

            // adaug tranzactia in lista de tranzactii a contului
            account.addTransaction(transaction);
            return false;
        }

        // altfel inseamna ca e ok daca nu trb sa mai verific si minBalance!!!!
        account.setBalance(account.getBalance() - amountConverted);

        // creez tranzactia
        PayOnlineOutput transaction = new PayOnlineOutput(timestamp,
                "Card payment", amountConverted, commerciant);

        // adaug tranzactia in lista de tranzactii a contului
        account.addTransaction(transaction);


        return true;
    }
}
