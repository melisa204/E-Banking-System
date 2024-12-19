package org.poo.fileio.output;

public class TransactionOutput {
    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    private int timestamp;

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    private String description;

    public TransactionOutput(final int timestamp, final String description) {
        this.timestamp = timestamp;
        this.description = description;
    }
}
