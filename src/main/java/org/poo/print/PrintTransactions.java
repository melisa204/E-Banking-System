package org.poo.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.Account;
import org.poo.User;
import org.poo.fileio.output.TransactionOutput;
import org.poo.transactions.BaseCommand;
import org.poo.transactions.Command;
import org.poo.utils.Admin;

import java.util.ArrayList;

public class PrintTransactions extends BaseCommand {
    User user;
    Admin admin;
    ArrayNode output;
    private ObjectMapper objectMapper = new ObjectMapper();
    public PrintTransactions(User user, String command, int timestamp, Admin admin, ArrayNode output) {
        super(command, timestamp);
        this.user = user;
        this.admin = admin;
        this.output = output;
    }

    @Override
    public void execute() {
        for (Account account : user.getAccounts()) {
            output.add(createOutput(account.getTransactions()));
        }
    }

    public ObjectNode createOutput(ArrayList<TransactionOutput> transactionOutput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", this.getCommand());
        objectNode.put("output", objectMapper.valueToTree(transactionOutput));
        objectNode.put("timestamp", this.getTimestamp());

        System.out.println("Am format outputul de tranzactii ");

        return objectNode;
    }
}
