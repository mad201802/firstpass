package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.DeleteEntryRequest;
import io.firstpass.ipc.communication.response.SimpleStatusResponse;
import io.firstpass.ipc.exceptions.IPCException;

public class DeleteEntryCallback {
    public static SimpleStatusResponse call(DeleteEntryRequest request) throws IPCException {
        if(FirstPass.passwordManager == null)
            throw new IPCException(400, "Database not loaded");
        // Check if category exists
        if(FirstPass.passwordManager.getAllEntries().stream().filter(entry -> entry.getId() == request.id).findAny().isEmpty())
            throw new IPCException(400, "Entry does not exist");

        if (FirstPass.passwordManager.deleteEntryById(request.id)) {
            return new SimpleStatusResponse();
        }

        throw new IPCException(500, "Failed to delete entry");
    }
}
