package org.poo.print;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.User;
import org.poo.fileio.input.CommandInput;
import org.poo.fileio.output.UserOutput;
import org.poo.transactions.BaseCommand;
import org.poo.transactions.Command;
import org.poo.utils.Admin;

import java.util.ArrayList;

public class PrintUsers extends BaseCommand {
    ArrayList<User> users;
    ArrayNode output;
    ArrayList<UserOutput> userOutputs = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    public PrintUsers(String command, int timestamp, ArrayList<User> users, ArrayNode output) {
        super(command, timestamp);
        this.users = users;
        this.output = output;
    }

    public void execute() {
        for (User user : users) {
            UserOutput userOutput = new UserOutput(user);
            userOutputs.add(userOutput);
        }

        output.add(createOutput());
    }

    public ObjectNode createOutput() {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", this.getCommand());
        objectNode.put("output", objectMapper.valueToTree(userOutputs));
        objectNode.put("timestamp", this.getTimestamp());
        return objectNode;
    }

}
