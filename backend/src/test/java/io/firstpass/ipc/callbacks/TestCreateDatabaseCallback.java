package io.firstpass.ipc.callbacks;

import io.firstpass.ipc.communication.request.CreateDatabaseRequest;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.*;
import util.Utils;

public class TestCreateDatabaseCallback {

    @Test()
    public void test_successfully_create_database() throws IPCException {
        Utils.initConfigurationInstance();
        CreateDatabaseRequest request = new CreateDatabaseRequest();
        request.filepath = Utils.DATABASE_NAME;
        request.masterpassword = Utils.MASTER_PASSWORD;

        OpenDatabaseResponse response = CreateDatabaseCallback.call(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.entries.size());
        Assertions.assertEquals(1, response.categories.size());
    }

    @Test()
    public void test_error_create_database() {
        CreateDatabaseRequest request = new CreateDatabaseRequest();
        request.filepath = Utils.DATABASE_NAME;
        request.masterpassword = Utils.MASTER_PASSWORD;

        Assertions.assertThrows(IPCException.class, () -> CreateDatabaseCallback.call(request));
    }

    @AfterEach()
    void afterEach() throws IPCException {
        Utils.teardown();
    }

}
