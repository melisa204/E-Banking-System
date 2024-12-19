package org.poo.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.Account;
import org.poo.User;
import org.poo.fileio.output.TransactionOutput;
import org.poo.commands.BaseCommand;
import org.poo.utils.Admin;

import java.util.ArrayList;

public class PrintTransactions extends BaseCommand {
    private User user;
    private Admin admin;
    private ArrayNode output;
    private ObjectMapper objectMapper = new ObjectMapper();
    public PrintTransactions(final User user, final String command,
                             final int timestamp, final Admin admin, final ArrayNode output) {
        super(command, timestamp);
        this.setUser(user);
        this.setAdmin(admin);
        this.setOutput(output);
    }

    /**
     * Execută logica pentru comanda de afișare a tranzacțiilor unui utilizator
     * (`printTransactions`).
     *
     * Funcționare:
     * - Iterează prin toate conturile utilizatorului curent.
     * - Pentru fiecare cont, generează un output JSON care conține lista de tranzacții
     * asociate contului.
     * - Adaugă output-ul generat în lista generală de output-uri.
     *
     * Scop:
     * - Permite afișarea tuturor tranzacțiilor asociate conturilor unui utilizator.
     *
     * Detalii:
     * - Lista de conturi este obținută prin `getUser().getAccounts()`.
     * - Output-ul fiecărui cont este generat utilizând metoda `createOutput` cu lista de
     * tranzacții.
     */
    @Override
    public void execute() {
        for (Account account : getUser().getAccounts()) {
            getOutput().add(createOutput(account.getTransactions()));
        }
    }
    /**
     * Creează un obiect JSON de tip `ObjectNode` care reprezintă output-ul tranzacțiilor unui
     * cont.
     *
     * Funcționare:
     * - Creează un nod JSON și adaugă următoarele câmpuri:
     *   - `command`: Numele comenzii curente (de exemplu, "printTransactions").
     *   - `output`: Lista de tranzacții asociate contului, serializată din `transactionOutput`.
     *   - `timestamp`: Timpul asociat execuției comenzii.
     *
     * Scop:
     * - Formatează lista de tranzacții ale unui cont într-un format JSON pentru afișare sau
     * procesare ulterioară.
     *
     * @param transactionOutput Lista de tranzacții asociate contului, fiecare reprezentată ca
     *                          `TransactionOutput`.
     * @return Un nod JSON (`ObjectNode`) care conține tranzacțiile contului sub formă de output
     * structurat.
     */
    public ObjectNode createOutput(final ArrayList<TransactionOutput> transactionOutput) {
        ObjectNode objectNode = getObjectMapper().createObjectNode();
        objectNode.put("command", this.getCommand());
        objectNode.put("output", getObjectMapper().valueToTree(transactionOutput));
        objectNode.put("timestamp", this.getTimestamp());

        return objectNode;
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
