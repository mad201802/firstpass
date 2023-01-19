package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.config.schemas.LoadedDB;
import io.firstpass.ipc.communication.response.GetLoadedDBsResponse;
import io.firstpass.ipc.communication.response.ListRecentDBsResponse;
import io.firstpass.ipc.exceptions.IPCException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ListRecentDBsCallback {

    public static ListRecentDBsResponse call(Object request) throws IPCException {
        if(FirstPass.configuration == null)
            throw new IPCException(400, "Configuration not loaded");

        ListRecentDBsResponse response = new ListRecentDBsResponse();

        ArrayList<LoadedDB> loadedDBs = new ArrayList<>();
        for(LoadedDB loadedDB : FirstPass.configuration.getConfig().recentDBs) {
            boolean exists = Files.exists(Path.of(loadedDB.filepath));
            if(exists) {
                loadedDBs.add(loadedDB);
            }
        }

        response.recentDBs = loadedDBs;

        FirstPass.configuration.getConfig().recentDBs = loadedDBs;
        FirstPass.configuration.saveConfig();

        return response;
    }
}
