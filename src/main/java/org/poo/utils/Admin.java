package org.poo.utils;

import org.poo.Account;
import org.poo.Card;
import org.poo.ExchangeRate;
import org.poo.User;
import org.poo.commands.Command;

import java.util.ArrayList;
import java.util.Set;

public final class Admin {
    private static Admin instance = null;
    private Admin() { }
    /**
     * Oferă instanța singleton a clasei `Admin`.
     *
     * Funcționare:
     * - Verifică dacă instanța singleton (`instance`) este `null`.
     * - Dacă este `null`, creează o nouă instanță a clasei `Admin`.
     * - Returnează instanța singleton existentă sau recent creată.
     *
     * Scop:
     * - Asigură că există o singură instanță a clasei `Admin` în cadrul aplicației.
     *
     * Detalii:
     * - Acest mecanism este util pentru centralizarea și gestionarea globală a datelor și
     * funcționalităților administrate de clasa `Admin`.
     * - Instanța este creată doar la prima cerere, implementând modelul "lazy initialization".
     *
     * @return Instanța singleton a clasei `Admin`.
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(final ArrayList<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(final ArrayList<Command> commands) {
        this.commands = commands;
    }

    private ArrayList<User> users = new ArrayList<>(); // aici am toti userii

    // aici am toate ratele de schimb
    private ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
    private ArrayList<Command> commands = new ArrayList<>(); // aici am toate comenzile

    /**
     * Caută și returnează un utilizator pe baza adresei de email.
     *
     * Funcționare:
     * - Iterează prin lista de utilizatori (`users`).
     * - Compară adresa de email a fiecărui utilizator cu adresa specificată prin `email`.
     * - Dacă este găsit un utilizator cu adresa de email potrivită, acesta este returnat.
     * - Dacă nu există niciun utilizator cu adresa de email specificată, returnează `null`.
     *
     * Scop:
     * - Permite găsirea unui utilizator în baza adresei de email.
     *
     * @param email Adresa de email utilizată pentru căutare.
     * @return Utilizatorul corespunzător, dacă există; altfel, `null`.
     */
    public User getUserByEmail(final String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) { // sau cu egal??
                return user;
            }
        }
        return null;
    }
    /**
     * Adaugă un utilizator în lista de utilizatori.
     *
     * Funcționare:
     * - Primește un obiect de tip `User` și îl adaugă în lista internă de utilizatori (`users`).
     *
     * Scop:
     * - Permite gestionarea utilizatorilor din sistem prin adăugarea unui nou utilizator.
     *
     * Detalii:
     * - Utilizatorul adăugat devine disponibil pentru operațiuni ulterioare asociate sistemului.
     *
     * @param user Obiect de tip `User` care urmează să fie adăugat în lista de utilizatori.
     */
    public void addUser(final User user) {
        users.add(user);
    }
    /**
     * Adaugă o rată de schimb valutar în lista de rate de schimb.
     *
     * Funcționare:
     * - Primește un obiect de tip `ExchangeRate` și îl adaugă în lista internă de rate de
     * schimb (`exchangeRates`).
     *
     * Scop:
     * - Permite gestionarea ratelor de schimb prin adăugarea unei noi rate în sistem.
     *
     * Detalii:
     * - Rata de schimb adăugată devine disponibilă pentru operațiuni de conversie valutară
     * ulterioare.
     *
     * @param exchangeRate Obiect de tip `ExchangeRate` care urmează să fie adăugat în lista de
     *                     rate de schimb.
     */
    public void addExchangeRate(final ExchangeRate exchangeRate) {
        exchangeRates.add(exchangeRate);
    }
    /**
     * Adaugă o comandă în lista de comenzi.
     *
     * Funcționare:
     * - Primește un obiect de tip `Command` și îl adaugă în lista internă de comenzi (`commands`).
     *
     * Scop:
     * - Permite gestionarea și execuția comenzilor prin adăugarea unei noi comenzi în sistem.
     *
     * Detalii:
     * - Comanda adăugată va fi disponibilă pentru execuție ulterioară.
     *
     * @param command Obiect de tip `Command` care urmează să fie adăugat în lista de comenzi.
     */
    public void addCommand(final Command command) {
        commands.add(command);
    }
    /**
     * Caută și returnează un cont pe baza IBAN-ului specificat.
     *
     * Funcționare:
     * - Iterează prin lista de utilizatori (`users`).
     * - Pentru fiecare utilizator, verifică lista sa de conturi.
     * - Compară IBAN-ul fiecărui cont cu IBAN-ul specificat.
     * - Dacă este găsit un cont cu IBAN-ul corespunzător, acesta este returnat.
     * - Dacă utilizatorul nu are conturi sau dacă niciun cont nu are IBAN-ul specificat,
     * returnează `null`.
     *
     * Scop:
     * - Permite identificarea unui cont din sistem pe baza IBAN-ului.
     *
     * Detalii:
     * - Afișează mesaje de diagnostic în timpul căutării pentru a facilita depanarea.
     *
     * @param iban IBAN-ul contului căutat.
     * @return Contul corespunzător, dacă există; altfel, `null`.
     */
    public Account getAccountByIban(final String iban) {
        for (User user : users) {
            if (user.getAccounts() == null) {
                return null;
            }
            for (Account account : user.getAccounts()) {
                if (account.getIban().equals(iban)) {
                    return account;
                }
            }
        }
        return null;
    }
    /**
     * Elimină un cont din lista de conturi a utilizatorului asociat.
     *
     * Funcționare:
     * - Iterează prin lista de utilizatori (`users`).
     * - Verifică pentru fiecare utilizator dacă are conturi asociate.
     * - Găsește contul care corespunde IBAN-ului specificat.
     * - Odată găsit, efectuează următoarele acțiuni:
     *   - Șterge toate cardurile atașate contului.
     *   - Șterge toate tranzacțiile asociate contului.
     *   - Elimină contul din lista de conturi a utilizatorului.
     * - Dacă contul este eliminat, metoda se oprește.
     *
     * Scop:
     * - Permite ștergerea completă a unui cont și a datelor sale asociate
     * (carduri și tranzacții) din sistem.
     *
     * Detalii:
     * - Dacă utilizatorul nu are conturi, metoda nu face nimic.
     * - Dacă contul specificat nu este găsit, metoda nu face nicio modificare.
     *
     * @param account Contul care urmează să fie eliminat.
     */
    public void removeAccount(final Account account) {
        for (User user : users) {
            if (user.getAccounts() == null) {
                return;
            }
            for (Account acc : user.getAccounts()) {
                if (acc.getIban().equals(account.getIban())) {
                    // mai intai sterg cardurile atasate contului
                    for (int i = 0; i < acc.getCards().size(); i++) {
                        acc.getCards().remove(i);
                    }
                    // sterg si tranzaactiile contului
                    for (int i = 0; i < acc.getTransactions().size(); i++) {
                        acc.getTransactions().remove(i);
                    }
                    user.getAccounts().remove(acc);
                    return;
                }
            }
        }
    }
    /**
     * Golește toate datele gestionate de Admin.
     *
     * Funcționare:
     * - Șterge toți utilizatorii din lista de utilizatori (`users`).
     * - Șterge toate ratele de schimb din lista de rate (`exchangeRates`).
     * - Șterge toate comenzile din lista de comenzi (`commands`).
     *
     * Scop:
     * - Resetează complet datele gestionate de Admin, lăsând listele interne goale.
     *
     * Detalii:
     * - Acțiunea este ireversibilă și șterge toate informațiile stocate.
     */
    public void clear() {
        users.clear();
        exchangeRates.clear();
        commands.clear();
    }
    /**
     * Resetează instanța singleton a clasei `Admin`.
     *
     * Funcționare:
     * - Setează câmpul static `instance` al clasei `Admin` la `null`.
     * - Permite recrearea unei noi instanțe a singleton-ului atunci când este necesar.
     *
     * Scop:
     * - Resetează starea singleton-ului pentru a permite inițializări proaspete.
     *
     * Detalii:
     * - Utilizată de obicei în testare sau atunci când trebuie recreată o instanță cu date noi.
     */
    public static void resetInstance() {
        instance = null;
    }
    /**
     * Caută și returnează un card pe baza numărului său și a utilizatorului asociat.
     *
     * Funcționare:
     * - Iterează prin lista de conturi ale utilizatorului specificat (`user.getAccounts()`).
     * - Pentru fiecare cont, verifică lista de carduri asociate (`account.getCards()`).
     * - Compară numărul fiecărui card cu numărul specificat (`cardNumber`).
     * - Dacă este găsit un card cu numărul corespunzător, îl returnează.
     * - Dacă nu este găsit niciun card cu numărul specificat, returnează `null`.
     *
     * Scop:
     * - Permite identificarea unui card al unui utilizator pe baza numărului cardului.
     *
     * Detalii:
     * - Funcția presupune că utilizatorul furnizat are conturi asociate.
     * - Dacă utilizatorul nu are conturi sau niciun card nu corespunde numărului, returnează
     * `null`.
     *
     * @param cardNumber Numărul cardului căutat.
     * @param user Utilizatorul al cărui card este căutat.
     * @return Cardul corespunzător, dacă există; altfel, `null`.
     */
    public Card getCardByNumber(final String cardNumber, final User user) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
                }
            }
        return null;
    }
    /**
     * Șterge un card pe baza numărului său și a utilizatorului asociat.
     *
     * Funcționare:
     * - Iterează prin lista de conturi ale utilizatorului specificat (`user.getAccounts()`).
     * - Pentru fiecare cont, verifică lista de carduri asociate (`account.getCards()`).
     * - Compară numărul fiecărui card cu numărul specificat (`cardNumber`).
     * - Dacă este găsit un card cu numărul corespunzător:
     *   - Este eliminat din lista de carduri a contului.
     *   - Metoda returnează `true`.
     * - Dacă nu este găsit niciun card cu numărul specificat, returnează `false`.
     *
     * Scop:
     * - Permite ștergerea unui card specific al unui utilizator din sistem.
     *
     * Detalii:
     * - Dacă utilizatorul nu are conturi sau niciun card nu corespunde numărului specificat,
     * metoda returnează `false`.
     *
     * @param cardNumber Numărul cardului care urmează să fie șters.
     * @param user Utilizatorul al cărui card este căutat și șters.
     * @return `true` dacă cardul a fost șters cu succes; `false` în caz contrar.
     */
    public boolean deleteCard(final String cardNumber, final User user) {
        for (Account account : user.getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    account.getCards().remove(card);
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Caută și returnează contul asociat unui card pe baza numărului cardului și al
     * utilizatorului.
     *
     * Funcționare:
     * - Iterează prin lista de conturi ale utilizatorului specificat (`user.getAccounts()`).
     * - Pentru fiecare cont, verifică lista de carduri asociate (`account.getCards()`).
     * - Compară numărul fiecărui card cu numărul specificat (`cardNumber`).
     * - Dacă este găsit un card cu numărul corespunzător, returnează contul asociat cardului.
     * - Dacă nu este găsit niciun card cu numărul specificat, returnează `null`.
     *
     * Scop:
     * - Permite identificarea contului asociat unui card al unui utilizator, folosind numărul
     * cardului.
     *
     * Detalii:
     * - Dacă utilizatorul nu are conturi sau niciun card din conturile sale nu corespunde
     * numărului specificat, metoda returnează `null`.
     *
     * @param cardNumber Numărul cardului căutat.
     * @param user Utilizatorul al cărui cont asociat cardului este căutat.
     * @return Contul asociat cardului, dacă există; altfel, `null`.
     */
    public Account getAccountByCardNumber(final String cardNumber, final User user) {
        for (Account account : user.getAccounts()) {
            for (Card card : account.getCards()) {
                if (card.getCardNumber().equals(cardNumber)) {
                    return account;
                }
            }
        }
        return null;
    }
    /**
     * Caută și returnează un card pe baza numărului său, fără a verifica utilizatorul asociat.
     *
     * Funcționare:
     * - Iterează prin lista de utilizatori (`users`).
     * - Pentru fiecare utilizator, verifică lista de conturi asociate acestuia
     * (`user.getAccounts()`).
     * - Pentru fiecare cont, verifică lista de carduri asociate (`account.getCards()`).
     * - Compară numărul fiecărui card cu numărul specificat (`cardNumber`).
     * - Dacă este găsit un card cu numărul corespunzător, îl returnează.
     * - Dacă nu este găsit niciun card cu numărul specificat, returnează `null`.
     *
     * Scop:
     * - Permite identificarea unui card din sistem exclusiv pe baza numărului cardului,
     * indiferent de utilizator.
     *
     * Detalii:
     * - Dacă niciun utilizator, cont sau card din sistem nu corespunde numărului specificat,
     * metoda returnează `null`.
     *
     * @param cardNumber Numărul cardului căutat.
     * @return Cardul corespunzător, dacă există; altfel, `null`.
     */
    public Card getCardOnlyByNumber(final String cardNumber) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(cardNumber)) {
                        return card;
                    }
                }
            }
        }
        return null;
    }
    /**
     * Adaugă ratele de schimb inverse în lista de rate de schimb.
     *
     * Funcționare:
     * - Iterează prin lista curentă de rate de schimb (`exchangeRates`).
     * - Pentru fiecare rată de schimb, creează o rată inversă folosind:
     *   - Moneda de destinație devine moneda sursă.
     *   - Moneda sursă devine moneda de destinație.
     *   - Rata devine inversul celei originale (1 / rata originală).
     * - Adaugă rata inversă în lista de rate de schimb.
     *
     * Scop:
     * - Asigură că sistemul poate efectua conversii bidirecționale între valute.
     *
     * Detalii:
     * - Rata inversă este adăugată la lista `exchangeRates` după ce toate ratele originale au
     * fost procesate.
     * - Mesajele sunt afișate pentru fiecare rată inversă adăugată, pentru diagnosticare și
     * verificare.
     *
     * @note Această metodă modifică lista `exchangeRates` în loc, adăugând rate suplimentare.
     */
    public void addInverseRates() {
        int size = exchangeRates.size();

        for (int i = 0; i < size; i++) {
            ExchangeRate original = exchangeRates.get(i);

            // creez rata inversa
            ExchangeRate inverse = new ExchangeRate(original.getTo(), original.getFrom(),
                    1 / original.getRate());

            // o adaug la lista
            exchangeRates.add(inverse);
        }
    }
    /**
     * Calculează rata de schimb între două valute folosind căutare recursivă.
     *
     * Funcționare:
     * - Verifică dacă parametrii `from` și `to` sunt validați (nu sunt `null` sau goi).
     * - Asigură că nu se creează cicluri folosind un set de valute deja vizitate (`visited`).
     * - Caută rata de schimb directă între cele două valute în lista de rate.
     * - Dacă rata directă nu este găsită, caută recursiv prin ratele de schimb intermediare.
     * - Înmulțește ratele succesive pentru a calcula rata totală de schimb.
     * - Elimină moneda curentă din `visited` la revenirea din recursivitate.
     *
     * Scop:
     * - Permite calcularea unei rate de schimb între două valute, chiar dacă nu există o rată
     * directă.
     *
     * Detalii:
     * - Evită ciclurile utilizând structura `visited`.
     * - Returnează `0` dacă nu există nicio cale validă între valutele specificate.
     *
     * @param from Moneda sursă.
     * @param to Moneda destinație.
     * @param visited Un set de valute vizitate pentru a preveni ciclurile.
     * @return Rata de schimb calculată, sau `0` dacă nu există cale validă.
     */
    public double getExchangeRateFromTo(final String from, final String to,
                                        final Set<String> visited) {
        // verific daca e null
        if (from == null || to == null || from.isEmpty() || to.isEmpty()) {
            return 0;
        }

        // verific ca sa evit ciclurile
        if (visited.contains(from)) {
            return 0;
        }

        // vizitez valoarea curenta
        visited.add(from);

        // caut rata directa
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(from) && exchangeRate.getTo().equals(to)) {
                // am gasit o si o returnez
                return exchangeRate.getRate();
            }
        }

        // caut recursiv
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (exchangeRate.getFrom().equals(from)) {
                double intermediateRate = getExchangeRateFromTo(exchangeRate.getTo(), to, visited);
                if (intermediateRate > 0) { // Dacă există o cale validă
                    return exchangeRate.getRate() * intermediateRate;
                }
            }
        }

        visited.remove(from);

        return 0;
    }
    /**
     * Caută și returnează un cont pe baza unui alias.
     *
     * Funcționare:
     * - Iterează prin lista de utilizatori (`users`).
     * - Pentru fiecare utilizator, verifică lista de conturi asociate.
     * - Pentru fiecare cont, verifică dacă alias-ul nu este `null`.
     * - Compară alias-ul contului cu alias-ul specificat (`alias`).
     * - Dacă este găsit un cont cu alias-ul corespunzător, returnează contul.
     * - Dacă nu este găsit niciun cont cu alias-ul specificat, returnează `null`.
     *
     * Scop:
     * - Permite identificarea unui cont pe baza unui alias unic atribuit acestuia.
     *
     * Detalii:
     * - Dacă alias-ul unui cont este `null`, acesta este omis în verificare.
     * - Alias-ul este considerat unic în contextul tuturor utilizatorilor.
     *
     * @param alias Alias-ul contului căutat.
     * @return Contul corespunzător, dacă există; altfel, `null`.
     */
    public Account getAccountByAlias(final String alias) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                // prima data verific sa nu fie null
                if (account.getAlias() == null) {
                    continue;
                }
                if (account.getAlias().equals(alias)) {
                    return account;
                }
            }
        }
        return null;
    }
    /**
     * Caută și returnează contul asociat unui card pe baza numărului cardului.
     *
     * Funcționare:
     * - Iterează prin lista de utilizatori (`users`).
     * - Pentru fiecare utilizator, verifică lista de conturi asociate (`user.getAccounts()`).
     * - Pentru fiecare cont, verifică lista de carduri asociate (`account.getCards()`).
     * - Compară numărul fiecărui card cu numărul specificat (`iban`).
     * - Dacă este găsit un card cu numărul corespunzător, returnează contul asociat acestuia.
     * - Dacă nu este găsit niciun card cu numărul specificat, returnează `null`.
     *
     * Scop:
     * - Permite identificarea unui cont exclusiv pe baza numărului cardului asociat.
     *
     * Detalii:
     * - Dacă niciun utilizator, cont sau card din sistem nu corespunde numărului specificat,
     * metoda returnează `null`.
     * - Este utilă în scenarii unde se dorește identificarea contului asociat unui card, fără
     * alte informații suplimentare.
     *
     * @param iban Numărul cardului asociat contului căutat.
     * @return Contul asociat cardului, dacă există; altfel, `null`.
     */
    public Account getAccountOnlyByCardNumber(final String iban) {
        for (User user : users) {
            for (Account account : user.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(iban)) {
                        return account;
                    }
                }
            }
        }
        return null;
    }
}
