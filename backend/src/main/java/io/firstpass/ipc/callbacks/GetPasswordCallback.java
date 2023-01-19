package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.GetPasswordRequest;
import io.firstpass.ipc.communication.response.GetPasswordResponse;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.manager.models.EntryModel;

public class GetPasswordCallback {
    public static GetPasswordResponse call(GetPasswordRequest request) throws IPCException {
        if(FirstPass.passwordManager == null)
            throw new IPCException(400, "Database not loaded");
        // Check if category exists
        EntryModel entry = FirstPass.passwordManager.getEntryById(request.id);
        if(entry == null)
            throw new IPCException(400, "Entry does not exist");

        GetPasswordResponse response = new GetPasswordResponse();
        response.password = entry.getPassword();
        return response;
    }
}
