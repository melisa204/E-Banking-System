package org.poo.fileio.output;

public class DeleteAccountFailOutput extends DeleteAccountOutput {
    public final String getError() {
        return error;
    }

    public final void setError(final String error) {
        this.error = error;
    }

    private String error = "Account couldn't be deleted - see org.poo.transactions for details";
    public DeleteAccountFailOutput(final int timestamp) {
        super(timestamp);
    }
}
