package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.response.GetLoadedDBsResponse;
import io.firstpass.ipc.exceptions.IPCException;

public class GetLoadedDBsCallback {

    public static GetLoadedDBsResponse call(Object request) throws IPCException {
        if(FirstPass.passwordManager == null)
            throw new IPCException(400, "Database not loaded");
        GetLoadedDBsResponse response = new GetLoadedDBsResponse();
        response.loadedDBS = FirstPass.configuration.getConfig().recentDBs;
        return response;
    }
}
