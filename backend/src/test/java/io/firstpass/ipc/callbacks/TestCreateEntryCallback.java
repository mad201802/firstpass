package io.firstpass.ipc.callbacks;

import io.firstpass.ipc.communication.request.CreateEntryRequest;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestCreateEntryCallback {
    @Test()
    public void test_create_entry() throws IPCException {
        Utils.initPasswordManagerInstance();

        CreateEntryRequest request = new CreateEntryRequest();
        request.name = "Test Entry";
        request.username = "Test Username";
        request.password = "Test Password";
        request.category = 1;
        request.url = "https://test.com";
        request.notes = "Test Notes";

        CreateEntryCallback.call(request);

        Utils.teardown();
    }
}
