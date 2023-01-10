package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.encryption.exceptions.UnknownAlgorithmException;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.ipc.communication.request.CreateDatabaseRequest;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.manager.PasswordManager;
import org.junit.jupiter.api.*;
import util.Utils;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;

public class TestCreateDatabaseCallback {

    @BeforeAll()
    public static void beforeAll() {
        Utils.initConfigurationInstance();
        System.out.println("==================");
    }

    @Test()
    public void test_successfully_create_database() throws SQLException, UnknownAlgorithmException {
        CreateDatabaseRequest request = new CreateDatabaseRequest();
        request.filepath = Utils.DATABASE_NAME;
        request.masterpassword = Utils.MASTER_PASSWORD;

        try {
            OpenDatabaseResponse response = CreateDatabaseCallback.call(request);
            Assertions.assertNotNull(response);
            Assertions.assertEquals(0, response.entries.size());
            Assertions.assertEquals(8, response.categories.size());
        } catch (IPCException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll()
    public static void after() {
        Utils.teardown();
    }

}
