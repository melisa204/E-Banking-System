package org.poo;

import org.poo.fileio.input.UserInput;

import java.util.ArrayList;

public class User {
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

    private String firstName;
    private String lastName;
    private  String email;

    private ArrayList<Account> accounts = new ArrayList<>();

    public final ArrayList<Account> getAccounts() {
        return accounts;
    }

    public final void setAccounts(final ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public User(final UserInput user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
    /**
     * Adaugă un cont nou în lista de conturi ale utilizatorului.
     *
     * Funcționare:
     * - Contul specificat este adăugat la lista de conturi ale utilizatorului (`accounts`).
     *
     * Scop:
     * - Permite asocierea unui cont nou utilizatorului curent.
     *
     * Detalii:
     * - Metoda presupune că lista `accounts` permite duplicate, deci este responsabilitatea
     * apelantului să se asigure că nu se adaugă conturi duplicate dacă acest lucru este nedorit.
     *
     * @param account Contul care urmează să fie adăugat utilizatorului.
     */
    public void addNewAccount(final Account account) {
        this.accounts.add(account);
    }
    /**
     * Verifică dacă un cont specificat aparține utilizatorului.
     *
     * Funcționare:
     * - Iterează prin lista de conturi ale utilizatorului (`accounts`).
     * - Compară IBAN-ul fiecărui cont din listă cu IBAN-ul contului specificat
     * (`account.getIban()`).
     * - Dacă este găsit un cont cu IBAN-ul corespunzător, returnează `true`.
     * - Dacă nu este găsit niciun cont corespunzător, returnează `false`.
     *
     * Scop:
     * - Determină dacă un anumit cont este asociat utilizatorului curent.
     *
     * Detalii:
     * - Compararea se face pe baza IBAN-ului, presupunând că acesta este unic pentru fiecare cont.
     *
     * @param account Contul care urmează să fie verificat.
     * @return `true` dacă contul aparține utilizatorului; `false` în caz contrar.
     */
    public boolean hasAccount(final Account account) {
        for (Account acc : accounts) {
            if (acc.getIban().equals(account.getIban())) {
                return true;
            }
        }
        return false;
    }
    /**
     * Setează un alias pentru un cont pe baza IBAN-ului specificat.
     *
     * Funcționare:
     * - Iterează prin lista de conturi ale utilizatorului (`accounts`).
     * - Verifică dacă IBAN-ul contului curent corespunde cu IBAN-ul specificat (`iban`).
     * - Dacă este găsit un cont corespunzător, setează alias-ul pentru acel cont.
     *
     * Scop:
     * - Permite atribuirea unui alias unui cont, identificat unic prin IBAN.
     *
     * Detalii:
     * - Dacă IBAN-ul specificat nu este găsit în lista de conturi, metoda nu face nicio
     * modificare.
     * - Alias-ul poate fi utilizat ulterior pentru identificarea rapidă a contului.
     *
     * @param iban IBAN-ul contului pentru care se setează alias-ul.
     * @param alias Alias-ul care urmează să fie setat pentru cont.
     */
    public void setAlias(final String iban, final String alias) {
        for (Account account : accounts) {
            if (account.getIban().equals(iban)) {
                account.setAlias(alias);
            }
        }
    }
}
