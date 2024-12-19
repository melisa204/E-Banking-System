package org.poo.fileio.output;

public class DeleteAccountSuccessOutput extends DeleteAccountOutput {
    public final String getSuccess() {
        return success;
    }

    public final void setSuccess(final String success) {
        this.success = success;
    }

    private String success = "Account deleted";
    public DeleteAccountSuccessOutput(final int timestamp) {
        super(timestamp);
    }
}
