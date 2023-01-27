package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.DeleteEntryRequest;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestDeleteEntryCallback {
    @Test()
    public void test_delete_entry_callback() throws IPCException {
        Utils.initPasswordManagerInstance();
        FirstPass.passwordManager.createEntry("Test Entry", "Test Username", "Test Password", 1, "https://test.com", "Test Notes");

        DeleteEntryRequest request = new DeleteEntryRequest();
        request.id = 1;

        DeleteEntryCallback.call(request);

        Assertions.assertEquals(0, FirstPass.passwordManager.getAllEntries().size());
        Utils.teardown();
    }
}
