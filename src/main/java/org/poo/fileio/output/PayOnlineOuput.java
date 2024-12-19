package org.poo.fileio.output;

public class PayOnlineOuput {
    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    int timestamp;
    String description = "Card not found";

    public PayOnlineOuput(int timestamp) {
        this.timestamp = timestamp;
    }
}
