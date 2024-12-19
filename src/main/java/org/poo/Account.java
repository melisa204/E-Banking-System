package org.poo;

import org.poo.fileio.output.SplitPaymentOutput;
import org.poo.fileio.output.TransactionOutput;
import org.poo.utils.Admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Account {
    public final String getIban() {
        return iban;
    }

    public final void setIban(final String iban) {
        this.iban = iban;
    }

    public final double getBalance() {
        return balance;
    }

    public final void setBalance(final double balance) {
        this.balance = balance;
    }

    public final String getCurrency() {
        return currency;
    }

    public final void setCurrency(final String currency) {
        this.currency = currency;
    }

    public final ArrayList<TransactionOutput> getTransactions() {
        return transactions;
    }

    public final void setTransactions(final ArrayList<TransactionOutput> transactions) {
        this.transactions = transactions;
    }

    private String  iban;
    private double balance;
    private String currency;
    private String accountType;
    private double interestRate;

    public final String getAlias() {
        return alias;
    }

    public final void setAlias(final String alias) {
        this.alias = alias;
    }

    private String alias;

    public final double getMinBalance() {
        return minBalance;
    }

    public final void setMinBalance(final double minBalance) {
        this.minBalance = minBalance;
    }

    private double minBalance;

    public final String getAccountType() {
        return accountType;
    }

    public final void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public final double getInterestRate() {
        return interestRate;
    }

    public final void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }
    private ArrayList<TransactionOutput> transactions = new ArrayList<TransactionOutput>();

    public final ArrayList<Card> getCards() {
        return cards;
    }

    public final void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }

    private ArrayList<Card> cards = new ArrayList<Card>();

    public Account(final String currency, final String accountType, final double interestRate,
                   final double balance, final String iban) {
        this.currency = currency;
        this.accountType = accountType;
        // verific ce tip de cont este
        if (this.accountType.equals("savings")) {
            // doar daca e de tipul asta are interestRate
            this.interestRate = interestRate;
        }
        this.balance = balance;
        this.iban = iban;
    }
    /**
     * Adaugă o tranzacție în lista de tranzacții asociată contului.
     *
     * Funcționare:
     * - Primește un obiect de tip `TransactionOutput` care reprezintă detaliile tranzacției.
     * - Adaugă tranzacția în lista internă de tranzacții a contului (`transactions`).
     *
     * Scop:
     * - Permite gestionarea istoricului tranzacțiilor pentru un cont.
     *
     * @param transaction Obiect de tip `TransactionOutput` care conține detaliile tranzacției de
     *                    adăugat.
     */
    public final void addTransaction(final TransactionOutput transaction) {
        this.transactions.add(transaction);
    }
    /**
     * Adaugă un card în lista de carduri asociată contului.
     *
     * Funcționare:
     * - Primește un obiect de tip `Card` și îl adaugă în lista internă de carduri a
     * contului (`cards`).
     *
     * Scop:
     * - Permite gestionarea cardurilor asociate unui cont.
     *
     * Detalii:
     * - Cardurile adăugate devin disponibile pentru operațiuni ulterioare asociate contului.
     *
     * @param card Obiect de tip `Card` care urmează să fie asociat contului.
     */

    public final void addCard(final Card card) {
        this.cards.add(card);
    }
    /**
     * Setează balanța minimă permisă pentru cont.
     *
     * Funcționare:
     * - Atribuie valoarea specificată prin `minBalance` câmpului `minBalance` al contului.
     *
     * Scop:
     * - Permite definirea unei balanțe minime sub care contul nu ar trebui să scadă.
     *
     * Detalii:
     * - Această metodă nu efectuează verificări suplimentare asupra valorii `minBalance`.
     *   Se presupune că valoarea furnizată este validă.
     *
     * @param newMinBalance Noua valoare a balanței minime pentru cont.
     */
    public final void setMinimumBalance(final double newMinBalance) {
        this.minBalance = newMinBalance;
    }
    /**
     * Realizează logica transferului de bani între două conturi.
     *
     * Funcționare:
     * - Verifică dacă conturile expeditor și destinatar au aceeași monedă (`currency`):
     *   - Dacă da:
     *     - Verifică dacă expeditorul are suficiente fonduri pentru transfer.
     *     - Dacă nu există suficiente fonduri, creează o tranzacție cu mesajul "Insufficient
     *     funds"
     *       și returnează `false`.
     *     - Dacă fondurile sunt suficiente, scade suma din balanța expeditorului și o adaugă în
     *     balanța destinatarului.
     *   - Dacă monedele diferă:
     *     - Obține rata de schimb dintre valutele celor două conturi utilizând metoda
     *     `getExchangeRateFromTo`.
     *     - Dacă rata de schimb nu este disponibilă (`finalRate == 0`), returnează `false`.
     *     - Convertește suma utilizând rata de schimb și verifică din nou fondurile.
     *     - Dacă expeditorul nu are suficiente fonduri, creează o tranzacție cu mesajul
     *     "Insufficient funds"
     *       și returnează `false`.
     *     - Dacă fondurile sunt suficiente, efectuează transferul, ajustând balanțele celor două
     *     conturi.
     *
     * Scop:
     * - Permite transferul de fonduri între două conturi, cu sau fără conversie valutară.
     * - Înregistrează tranzacții relevante în cazul eșecului din cauza fondurilor insuficiente.
     *
     * Detalii:
     * - Conversia valutară utilizează o metodă de obținere a ratei de schimb din `Admin`.
     * - Dacă rata de schimb nu este disponibilă sau balanța este insuficientă, transferul este
     * anulat.
     * - Metoda returnează `true` dacă transferul reușește și `false` în caz contrar.
     *
     * @param receiver Contul destinatarului.
     * @param amount Suma de transferat.
     * @param admin Instanța `Admin` pentru obținerea ratei de schimb.
     * @param timestamp Timpul asociat tranzacției.
     * @return `true` dacă transferul reușește, `false` în caz contrar.
     */
    public boolean sendMoney(final Account receiver, final double amount, final Admin admin,
                             final int timestamp) {
        // verific daca au aceeasi moneda
        if (this.getCurrency().equals(receiver.getCurrency())) {
            if (this.getBalance() < amount) {
                TransactionOutput transaction = new TransactionOutput(timestamp,
                        "Insufficient funds");

                // adaug tranzactia in lista de tranzactii a contului
                this.addTransaction(transaction);

                return false;
            }

            // am verificat si totul e ok -> fac tfransferul
            this.balance -= amount;
            receiver.balance += amount;

            return true;
        }

        // au currency diferit, deci trebuie sa fac conversie
        double finalRate = admin.getExchangeRateFromTo(currency, receiver.currency,
                new HashSet<>());

        if (finalRate == 0) {
            return false;
        }

        // ca sa stiu cum sa realizez calculul, decid ce tip de rata am
        double amountConverted = amount * finalRate;

        if (amount > balance) {
            TransactionOutput transaction = new TransactionOutput(timestamp, "Insufficient funds");

            // adaug tranzactia in lista de tranzactii a contului
            this.addTransaction(transaction);

            return false;
        }

        // am verificat si totul e ok -> fac tfransferul
        this.balance -= amount;
        receiver.balance += amountConverted;

        return true;
    }
    /**
     * Modifică rata dobânzii asociată contului.
     *
     * Funcționare:
     * - Setează valoarea `interestRate` a contului la valoarea specificată prin
     * `newInterestRate`.
     *
     * Scop:
     * - Permite ajustarea ratei dobânzii aplicate contului.
     *
     * Detalii:
     * - Această metodă nu efectuează verificări suplimentare asupra valorii ratei dobânzii.
     *   Se presupune că valoarea furnizată este validă.
     *
     * @param newInterestRate Noua rată a dobânzii care va fi asociată contului.
     */

    public void changeInterestRate(final double newInterestRate) {
        this.interestRate = newInterestRate;
    }
    /**
     * Verifică dacă există suficiente fonduri în cont pentru o anumită sumă și monedă.
     *
     * Funcționare:
     * - Compară moneda contului (`this.currency`) cu moneda specificată (`givenCurrency`):
     *   - Dacă monedele sunt identice:
     *     - Verifică dacă balanța contului este suficientă pentru suma dată.
     *   - Dacă monedele diferă:
     *     - Obține rata de schimb dintre monede utilizând metoda `getExchangeRateFromTo` din
     *     `Admin`.
     *     - Convertește suma specificată (`amount`) în moneda contului.
     *     - Verifică dacă balanța contului este suficientă pentru suma convertită.
     * - Returnează `true` dacă există fonduri suficiente și `false` în caz contrar.
     *
     * Scop:
     * - Asigură validarea fondurilor disponibile pentru tranzacții în aceeași monedă sau în
     * monede diferite.
     *
     * Detalii:
     * - Dacă rata de schimb nu este disponibilă (`finalRate == 0`), metoda returnează `false`.
     * - Compară balanța contului fie cu suma specificată (dacă monedele sunt identice), fie cu suma
     * convertită.
     *
     * @param amount Suma care trebuie verificată.
     * @param givenCurrency Moneda în care este exprimată suma.
     * @param admin Instanța `Admin` pentru obținerea ratei de schimb.
     * @return `true` dacă există suficiente fonduri, `false` în caz contrar.
     */
    public boolean checkBalance(final double amount, final String givenCurrency,
                                final Admin admin) {
        // verific daca e aceeasi moneda
        if (this.currency.equals(givenCurrency)) {
            if (this.balance < amount) {
                // inseamna ca nu am destui bani
                return false;
            }

            // inseamna ca am destui bani
            return true;
        }

        // inseamna ca trb sa fac transferul
        double finalRate = admin.getExchangeRateFromTo(givenCurrency, this.currency,
                new HashSet<>());

        if (finalRate == 0) {
            return false;
        }

        double amountConverted = amount * finalRate;

        if (this.balance < amountConverted) {
            return false;
        }

        return true;
    }
    /**
     * Realizează logica pentru finalizarea unei plăți împărțite (`splitPayment`).
     *
     * Funcționare:
     * - Verifică dacă moneda contului este aceeași cu moneda plății:
     *   - Dacă monedele sunt identice:
     *     - Scade suma împărțită (`splitAmount`) din balanța contului.
     *     - Creează un mesaj descriptiv al plății și o tranzacție corespunzătoare.
     *     - Adaugă tranzacția în istoricul contului.
     *   - Dacă monedele diferă:
     *     - Obține rata de schimb dintre monedele implicate utilizând metoda
     *     `getExchangeRateFromTo` din `Admin`.
     *     - Convertește suma împărțită (`splitAmount`) în moneda contului.
     *     - Ajustează balanța contului scăzând suma convertită.
     *     - Creează un mesaj descriptiv al plății și o tranzacție corespunzătoare.
     *     - Adaugă tranzacția în istoricul contului.
     *
     * Scop:
     * - Permite realizarea unei plăți împărțite care implică unul sau mai multe conturi.
     * - Gestionează atât cazurile cu aceeași monedă, cât și cazurile care necesită conversie
     * valutară.
     *
     * Detalii:
     * - Dacă rata de schimb nu este disponibilă, implementarea trebuie extinsă pentru a gestiona
     * acest caz.
     * - Mesajele descriptive și tranzacțiile create reflectă detalii precum suma, moneda și
     * conturile implicate.
     *
     * @param splitAmount Suma împărțită pentru fiecare cont.
     * @param givenCurrency Moneda plății.
     * @param admin Instanța `Admin` pentru obținerea ratei de schimb.
     * @param timestamp Timpul asociat tranzacției.
     * @param amount Suma totală de împărțit.
     * @param accounts Lista conturilor implicate în plată.
     */
    public void splitPaymentDone(final double splitAmount, final String givenCurrency,
                                 final Admin admin,
                                 final int timestamp, final double amount,
                                 final List<String> accounts) {
        String message;
        // verific daca e aceeasi moneda
        if (this.currency.equals(givenCurrency)) {
            balance -= splitAmount;

            // creez mesajul
            message = String.format("Split payment of %.2f %s", amount, givenCurrency);

            // creez tranzactia
            SplitPaymentOutput transaction = new SplitPaymentOutput(timestamp, message,
                    givenCurrency, splitAmount, accounts);

            // adaug tranzactia in lista de tranzactii a contului
            this.addTransaction(transaction);

            return;
        }

        // inseamna ca trb sa fac transferul
        double finalRate = admin.getExchangeRateFromTo(givenCurrency, this.currency,
                new HashSet<>());

        double amountConverted = splitAmount * finalRate;

        balance -= amountConverted;

        // creez mesajul
        message = String.format("Split payment of %.2f %s", amount, givenCurrency);

        // creez tranzactia
        SplitPaymentOutput transaction = new SplitPaymentOutput(timestamp, message, givenCurrency,
                splitAmount, accounts);

        // adaug tranzactia in lista de tranzactii a contului
        this.addTransaction(transaction);
    }
}
