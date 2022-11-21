package io.firstpass.ipc.exceptions;

import java.util.ArrayList;

public class IPCException extends Exception {
    private final int status;
    private final String error;

    public IPCException(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
