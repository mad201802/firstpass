package util;

import io.firstpass.FirstPass;
import io.firstpass.config.Configuration;
import io.firstpass.config.schemas.DefaultConfig;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.manager.PasswordManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class Utils {
    public static String DATABASE_NAME = "unittests.fpdb", MASTER_PASSWORD = "SomeSecurePassword";
    public static void initPasswordManagerInstance() {
        IEncryptedDatabase database;
        ISymmetricEncryptionAlgorithm algo;
        try {
            database = new SQLiteDriver("unittests.fpdb", MASTER_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        algo = SymmetricEncryptionFactory.getSymmetricEncryption(database.getEncryptionAlgorithm());
        FirstPass.passwordManager = new PasswordManager(database, algo, MASTER_PASSWORD);
        System.out.println("Initialized PasswordManager instance.");
    }

    public static void initConfigurationInstance() {
        FirstPass.configuration = new Configuration<>( new DefaultConfig(), "", "unittests", false);
        System.out.println("Initialized configuration instance.");
    }

    public static void teardown() {
        if(FirstPass.passwordManager != null)
            FirstPass.passwordManager.closeDatabase();

        FirstPass.passwordManager = null;

        try {
            Files.deleteIfExists(new File(DATABASE_NAME).toPath());
        } catch (IOException e) {
            System.out.println("Failed to delete database file");
        }

        if(FirstPass.configuration != null)
            FirstPass.configuration.deleteConfigFile();
        FirstPass.configuration = null;
    }

}
