package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.EmptyRequest;
import io.firstpass.ipc.communication.response.SimpleStatusResponse;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.ipc.handler.IPCHandler;

public class CloseDatabaseCallback {
    public static SimpleStatusResponse call(EmptyRequest response) throws IPCException {
        if(FirstPass.passwordManager != null) {
            if (FirstPass.passwordManager.closeDatabase()) {
                FirstPass.passwordManager = null;
            } else {
                throw new IPCException(500, "Failed to close database");
            }
        } else {
            throw new IPCException(500, "No database is open");
        }

        return new SimpleStatusResponse();
    }
}
