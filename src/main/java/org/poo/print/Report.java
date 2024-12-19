package org.poo.print;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.Account;
import org.poo.commands.BaseCommand;
import org.poo.fileio.output.ReportOutput;
import org.poo.fileio.output.TransactionOutput;
import org.poo.utils.Admin;

import java.util.ArrayList;

public class Report extends BaseCommand {
    private int startTimestamp;
    private int endTimestamp;
    private String account;
    private Admin admin;
    private ArrayNode output;
    private ObjectMapper objectMapper = new ObjectMapper();

    public Report(final String command, final int timestamp, final int startTimestamp,
                   final int endTimestamp, final String account,
                   final Admin admin, final ArrayNode output) {
        super(command, timestamp);
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.account = account;
        this.setAdmin(admin);
        this.setOutput(output);
    }
    /**
     * Execută logica pentru comanda de generare a unui raport de tranzacții pe baza unui interval
     * de timp.
     *
     * Funcționare:
     * - Găsește contul asociat IBAN-ului specificat utilizând metoda `getAccountByIban` din
     * `Admin`.
     *   - Dacă contul nu este găsit, execuția este întreruptă.
     * - Iterează prin toate tranzacțiile contului pentru a selecta doar tranzacțiile care au
     * timestamp-ul în intervalul specificat (`startTimestamp` și `endTimestamp`).
     * - Creează un obiect `ReportOutput` care include:
     *   - IBAN-ul contului.
     *   - Balanța curentă a contului.
     *   - Valuta contului.
     *   - Lista tranzacțiilor care se încadrează în intervalul specificat.
     * - Adaugă raportul generat în lista de output pentru utilizare ulterioară.
     *
     * Scop:
     * - Permite utilizatorului să obțină un raport detaliat al tranzacțiilor dintr-un cont pentru
     * un anumit interval de timp.
     *
     * Detalii:
     * - Tranzacțiile incluse în raport trebuie să fie cuprinse între `startTimestamp` și
     * `endTimestamp`.
     * - Dacă nu există tranzacții în intervalul specificat, raportul generat va conține o listă
     * goală.
     */

    public void execute() {
        // extrag contul
        Account currentAccount = getAdmin().getAccountByIban(account);

        if (currentAccount == null) {
            return;
        }

        // selectez tranzactiile dintre timestampurile date -> declar o lista noua de tranzactii
        ArrayList<TransactionOutput> newTransactions = new ArrayList<>();

        for (TransactionOutput transaction : currentAccount.getTransactions()) {
            if (transaction.getTimestamp() >= startTimestamp
                    && transaction.getTimestamp() <= endTimestamp) {
                newTransactions.add(transaction);
            }
        }

        // instantiez clasa cu outputul corespunzator pentru report
        ReportOutput reportOutput = new ReportOutput(currentAccount.getIban(),
                currentAccount.getBalance(), currentAccount.getCurrency(), newTransactions);

        // afisez tranzactiile
        getOutput().add(createOutput(reportOutput));
    }
    /**
     * Creează un obiect JSON de tip `ObjectNode` care reprezintă raportul generat.
     *
     * Funcționare:
     * - Creează un nod JSON și adaugă următoarele câmpuri:
     *   - `command`: Numele comenzii curente (de exemplu, "report").
     *   - `output`: Detaliile raportului, serializate din obiectul `ReportOutput`.
     *   - `timestamp`: Timpul asociat execuției comenzii.
     *
     * Scop:
     * - Formatează raportul generat într-un format JSON pentru afișare sau utilizare ulterioară.
     *
     * @param reportOutput Obiect care conține detaliile raportului, inclusiv tranzacțiile și
     *                     informațiile contului.
     * @return Un nod JSON (`ObjectNode`) care reprezintă raportul generat.
     */
    public ObjectNode createOutput(final ReportOutput reportOutput) {
        ObjectNode objectNode = getObjectMapper().createObjectNode();
        objectNode.put("command", this.getCommand());
        objectNode.put("output", getObjectMapper().valueToTree(reportOutput));
        objectNode.put("timestamp", this.getTimestamp());

        return objectNode;
    }

    public final Admin getAdmin() {
        return admin;
    }

    public final void setAdmin(final Admin admin) {
        this.admin = admin;
    }

    public final ArrayNode getOutput() {
        return output;
    }

    public final void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public final ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public final void setObjectMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
