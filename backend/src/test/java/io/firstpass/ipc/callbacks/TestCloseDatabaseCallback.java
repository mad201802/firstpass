package io.firstpass.ipc.callbacks;

import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestCloseDatabaseCallback {
    @Test()
    public void test_close_database() throws IPCException {
        Utils.initPasswordManagerInstance();
        CloseDatabaseCallback.call(null);
        Utils.teardown();
    }

    @Test()
    public void test_close_database_error() {
        Assertions.assertThrows(IPCException.class, () -> CloseDatabaseCallback.call(null));
    }
}
