package org.poo.fileio.output;

public class DeleteAccountOutput {
    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    private int timestamp;

    public DeleteAccountOutput(final int timestamp) {
        this.timestamp = timestamp;
    }
}
