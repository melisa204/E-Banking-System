package org.poo.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.User;
import org.poo.fileio.output.UserOutput;
import org.poo.commands.BaseCommand;

import java.util.ArrayList;

public class PrintUsers extends BaseCommand {
    private ArrayList<User> users;
    private ArrayNode output;
    private ArrayList<UserOutput> userOutputs = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    public PrintUsers(final String command, final int timestamp,
                      final ArrayList<User> users, final ArrayNode output) {
        super(command, timestamp);
        this.setUsers(users);
        this.setOutput(output);
    }
    /**
     * Execută logica pentru comanda de afișare a utilizatorilor (`printUsers`).
     *
     * Funcționare:
     * - Iterează prin lista de utilizatori și creează un obiect `UserOutput` pentru fiecare
     * utilizator.
     * - Adaugă fiecare obiect `UserOutput` generat în lista de output-uri pentru utilizatori.
     * - Creează un nod JSON final care reprezintă lista utilizatorilor și adaugă acest nod în
     * output-ul general.
     *
     * Scop:
     * - Permite afișarea informațiilor despre toți utilizatorii gestionați de sistem.
     *
     * Detalii:
     * - Lista de utilizatori este obținută din contextul curent (`getUsers`).
     * - Output-ul final include detalii despre fiecare utilizator sub formă de obiect JSON.
     */
    public void execute() {
        for (User user : getUsers()) {
            UserOutput userOutput = new UserOutput(user);
            getUserOutputs().add(userOutput);
        }

        getOutput().add(createOutput());
    }
    /**
     * Creează un obiect JSON de tip `ObjectNode` care reprezintă output-ul comenzii de afișare a
     * utilizatorilor.
     *
     * Funcționare:
     * - Creează un nod JSON și adaugă următoarele câmpuri:
     *   - `command`: Numele comenzii curente (de exemplu, "printUsers").
     *   - `output`: Lista de utilizatori, serializată din lista de `UserOutput`.
     *   - `timestamp`: Timpul asociat execuției comenzii.
     *
     * Scop:
     * - Formatează lista utilizatorilor într-un format JSON pentru afișare sau procesare
     * ulterioară.
     *
     * @return Un nod JSON (`ObjectNode`) care conține detaliile utilizatorilor sub forma unui
     * output structurat.
     */
    public ObjectNode createOutput() {
        ObjectNode objectNode = getObjectMapper().createObjectNode();
        objectNode.put("command", this.getCommand());
        objectNode.put("output", getObjectMapper().valueToTree(getUserOutputs()));
        objectNode.put("timestamp", this.getTimestamp());
        return objectNode;
    }

    public final ArrayList<User> getUsers() {
        return users;
    }

    public final void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public final ArrayNode getOutput() {
        return output;
    }

    public final void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public final ArrayList<UserOutput> getUserOutputs() {
        return userOutputs;
    }

    public final void setUserOutputs(final ArrayList<UserOutput> userOutputs) {
        this.userOutputs = userOutputs;
    }

    public final ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public final void setObjectMapper(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
