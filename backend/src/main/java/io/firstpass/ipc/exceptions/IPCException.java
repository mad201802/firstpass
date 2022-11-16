package io.firstpass.ipc.exceptions;

import java.util.ArrayList;

public class IPCException extends Exception {
    private final int status;
    private final ArrayList<String> errors = new ArrayList<>();

    public IPCException(int status, String error) {
        this.status = status;
        this.errors.add(error);
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
