package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.UpdateEntryRequest;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestUpdateEntryCallback {
    @Test()
    public void test_update_entry_callback() throws IPCException {
        Utils.initPasswordManagerInstance();
        FirstPass.passwordManager.createEntry("Test Entry", "Test Username", "Test Password", 1, "https://test.com", "Test Notes");

        UpdateEntryRequest request = new UpdateEntryRequest();
        request.id = 1;
        request.name = "Changed Name";
        request.username = "Changed Username";
        request.password = "Changed Password";
        request.url = "https://changed.com";
        request.category = 1;
        request.notes = "Changed Notes";

        UpdateEntryCallback.call(request);

        Utils.teardown();
    }
}
