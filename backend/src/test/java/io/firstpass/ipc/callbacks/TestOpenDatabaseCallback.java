package io.firstpass.ipc.callbacks;

import io.firstpass.ipc.communication.request.OpenDatabaseRequest;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestOpenDatabaseCallback {
    @Test()
    public void test_open_database_callback_throws_error() {
        Utils.initPasswordManagerInstance();

        OpenDatabaseRequest request = new OpenDatabaseRequest();
        request.filepath = "test.fpdb";
        request.masterpassword = "test";

        Assertions.assertThrows(IPCException.class, () -> OpenDatabaseCallback.call(request));

        Utils.teardown();
    }

    @Test()
    public void test_open_database_callback_file_not_found() {
        OpenDatabaseRequest request = new OpenDatabaseRequest();
        request.filepath = Utils.DATABASE_NAME;
        request.masterpassword = Utils.MASTER_PASSWORD;

        Assertions.assertThrows(IPCException.class, () -> OpenDatabaseCallback.call(request));
    }
}
