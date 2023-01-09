package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.database.models.CategoryModel;
import io.firstpass.encryption.exceptions.UnknownAlgorithmException;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.ipc.communication.request.CreateDatabaseRequest;
import io.firstpass.ipc.communication.request.LoadDatabaseRequest;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.manager.PasswordManager;
import io.firstpass.manager.models.EntryModel;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class LoadDatabaseCallback {

    public static OpenDatabaseResponse call(LoadDatabaseRequest request) throws IPCException {
        if (FirstPass.passwordManager != null) {
            throw new IPCException(69, "Database already open");
        }

        File file = new File(request.filepath);
        boolean exists = file.exists();

        if (!exists) {
            throw new IPCException(404, "File not found");
        }


        OpenDatabaseResponse response = new OpenDatabaseResponse();
        IEncryptedDatabase database;
        ISymmetricEncryptionAlgorithm algo;

        try {
            database = new SQLiteDriver(request.filepath, request.masterpassword);
        } catch (SQLException e) {
            throw new IPCException(500, "Failed to load database: " + e.getMessage());
        }

        try {
            algo = SymmetricEncryptionFactory.getSymmetricEncryption(database.getEncryptionAlgorithm());
        } catch (UnknownAlgorithmException uae) {
            throw new IPCException(500, "Defined encryption algorithm is not supported: " + uae.getMessage());
        }

        FirstPass.passwordManager = new PasswordManager(database, algo, request.masterpassword);
        ArrayList<CategoryModel> categories = (ArrayList<CategoryModel>) FirstPass.passwordManager.getAllCategories();

        ArrayList<EntryModel> entries = FirstPass.passwordManager.getAllEntries();

        entries.forEach(entry -> entry.setPassword(String.join("", Collections.nCopies(entry.getPassword().length(), "*"))));

        response.entries = entries;
        response.categories = categories;

        return response;
    }
}
