package org.poo.print;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.commands.BaseCommand;
import org.poo.utils.Admin;

public class SpendingReport extends BaseCommand {
    private int startTimestamp;
    private int endTimestamp;
    private String account;
    private Admin admin;
    private ArrayNode output;

    public SpendingReport(final String command, final int timestamp, final int startTimestamp,
                           final int endTimestamp, final String account, final  Admin admin,
                           final ArrayNode output) {
        super(command, timestamp);
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.account = account;
        this.setAdmin(admin);
        this.setOutput(output);
    }
    /**
     * Execută logica pentru generarea unui raport de cheltuieli (`Spendings Report`).
     *
     * **Scop planificat:**
     * - Calcularea și afișarea cheltuielilor efectuate într-un cont pe baza unui interval de
     * timp specificat.
     * - Gruparea cheltuielilor în funcție de categorie sau comerciant, dacă este cazul.
     *
     * **Notă:** Implementarea urmează să fie definită.
     */

    public void execute() {
        // TODO
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
}
