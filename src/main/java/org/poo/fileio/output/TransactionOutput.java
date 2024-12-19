package org.poo.fileio.output;

public class TransactionOutput {
    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    private int timestamp;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public TransactionOutput(int timestamp, String description) {
        this.timestamp = timestamp;
        this.description = description;
    }
}
