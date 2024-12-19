package org.poo.fileio.output;

public class DeleteAccountOutput {
    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    private int timestamp;

    public DeleteAccountOutput(int timestamp) {
        this.timestamp = timestamp;
    }
}
