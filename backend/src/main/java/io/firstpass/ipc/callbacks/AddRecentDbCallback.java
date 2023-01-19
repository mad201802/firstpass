package io.firstpass.ipc.callbacks;

import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.ipc.communication.request.AddRecentDbRequest;
import io.firstpass.ipc.exceptions.IPCException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;

public class AddRecentDbCallback {
    public static HashMap<String, String> call(AddRecentDbRequest request) throws IPCException {
        if(!Files.exists(Path.of(request.filepath))) {
            throw new IPCException(404, "File does not exist");
        }

        try {
            IEncryptedDatabase database = new SQLiteDriver(request.filepath);
            HashMap<String, String> response = database.getAllMeta();
            database.close();
            return response;
        } catch (SQLException e) {
            throw new IPCException(500, e.getMessage());
        }
    }
}
