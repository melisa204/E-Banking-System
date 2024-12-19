package org.poo.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.Account;
import org.poo.Card;
import org.poo.Transaction;
import org.poo.User;
import org.poo.fileio.output.DeleteAccountOutput;
import org.poo.fileio.output.PayOnlineOuput;
import org.poo.utils.Admin;

import javax.xml.crypto.dsig.TransformService;

public class PayOnline extends BaseCommand {
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommerciant() {
        return commerciant;
    }

    public void setCommerciant(String commerciant) {
        this.commerciant = commerciant;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String cardNumber;
    private double amount;
    private String currency;
    private String description;
    private String commerciant;
    Admin admin;
    private User user;
    ArrayNode output;
    private ObjectMapper objectMapper = new ObjectMapper();

    public PayOnline(User user, String command, int timestamp, String cardNumber, double amount, String currency, String description, String commerciant, Admin admin, ArrayNode output) {
        super(command, timestamp);
        this.user = user;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.commerciant = commerciant;
        this.admin = admin;
        this.output = output;
    }

    public void execute() {
        // verific daca cardul apartine userului
        Card card = admin.getCardByNumber(cardNumber, user);
        Account account = admin.getAccountByCardNumber(cardNumber, user);

        if (card == null) { // vad cum adaug in tranzactii!!!!!!!!!!!!
            PayOnlineOuput fail = new PayOnlineOuput(getTimestamp());
            output.add(createOutput(fail));
            return;
        }

        // efectuez plata
        boolean paid = card.payOnline(amount, currency, description, commerciant, account, admin);

        if (!paid) { // nu stiu daca ar trb sa se intample ceva
//            PayOnlineOuput fail = new PayOnlineOuput(getTimestamp());
//            output.add(createOutput(fail));
        }

        // creez tranzactia
        Transaction transaction = new Transaction("Online payment", getTimestamp());

        // adaug tranzactia in lista de tranzactii a contului
        account.addTransaction(transaction);
    }

    public ObjectNode createOutput(PayOnlineOuput fail) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", this.getCommand());
        objectNode.put("output", objectMapper.valueToTree(fail));
        objectNode.put("timestamp", this.getTimestamp());

        System.out.println("Nu am card pentru plata de pe cardul: " + cardNumber);

        return objectNode;
    }
}
