package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.config.schemas.LoadedDB;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.database.models.CategoryModel;
import io.firstpass.encryption.exceptions.UnknownAlgorithmException;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.ipc.communication.request.CreateDatabaseRequest;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.manager.PasswordManager;
import io.firstpass.manager.models.EntryModel;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class CreateDatabaseCallback {

    public static OpenDatabaseResponse call(CreateDatabaseRequest request) throws IPCException {
        if (FirstPass.passwordManager != null) {
            throw new IPCException(69, "Database already open");
        }

        if(Files.exists(new File(request.filepath).toPath())) {
            throw new IPCException(69, "Database already exists");
        }

        OpenDatabaseResponse response = new OpenDatabaseResponse();
        IEncryptedDatabase database;
        ISymmetricEncryptionAlgorithm algo;
        try {
            database = new SQLiteDriver(request.filepath, request.masterpassword);
        } catch (SQLException e) {
            throw new IPCException(500, "Failed to create database: " + e.getMessage());
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

        String[] filename_array = request.filepath.split("/");
        String filename = filename_array[filename_array.length-1];

        if(FirstPass.configuration == null) {
            throw new IPCException(500, "Configuration is not read");
        }

        if (FirstPass.configuration.getConfig().recentDBs.stream().noneMatch(loaded_db -> loaded_db.filepath.equals(request.filepath))) {
            FirstPass.configuration.getConfig().recentDBs.add(new LoadedDB(filename, request.filepath));
            FirstPass.configuration.saveConfig();
        }

        response.entries = entries;
        response.categories = categories;

        return response;
    }
}
