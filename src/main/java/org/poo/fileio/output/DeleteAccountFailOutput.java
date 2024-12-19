package org.poo.fileio.output;

public class DeleteAccountFailOutput extends DeleteAccountOutput {
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error = "Account couldn't be deleted - see org.poo.transactions for details";
    public DeleteAccountFailOutput(int timestamp) {
        super(timestamp);
    }
}
