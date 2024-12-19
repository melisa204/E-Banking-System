package org.poo.fileio.output;

public class DeleteAccountSuccessOutput extends DeleteAccountOutput {
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    private String success = "Account deleted";
    public DeleteAccountSuccessOutput(int timestamp) {
        super(timestamp);
    }
}
