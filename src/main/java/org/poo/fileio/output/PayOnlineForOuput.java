package org.poo.fileio.output;

public class PayOnlineForOuput {
    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    private int timestamp;
    private String description = "Card not found";

    public PayOnlineForOuput(final int timestamp) {
        this.setTimestamp(timestamp);
    }
}
