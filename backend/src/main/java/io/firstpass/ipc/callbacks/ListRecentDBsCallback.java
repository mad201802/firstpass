package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.response.ListRecentDBsResponse;
import io.firstpass.ipc.exceptions.IPCException;

public class ListRecentDBsCallback {

    public static ListRecentDBsResponse call(Object request) throws IPCException {
        if(FirstPass.configuration == null)
            throw new IPCException(400, "Configuration not initialized");
        ListRecentDBsResponse response = new ListRecentDBsResponse();
        response.loadedDBS = FirstPass.configuration.getConfig().recentDBs;
        return response;
    }
}
