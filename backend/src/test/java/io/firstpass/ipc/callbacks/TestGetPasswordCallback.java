package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.GetPasswordRequest;
import io.firstpass.ipc.communication.response.GetPasswordResponse;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestGetPasswordCallback {
    @Test()
    public void test_get_password_callback() throws IPCException {
        Utils.initPasswordManagerInstance();
        FirstPass.passwordManager.createEntry("Test", "Test", "Test", 1, "test", "test");

        GetPasswordRequest request = new GetPasswordRequest();
        request.id = 1;

        GetPasswordResponse response = GetPasswordCallback.call(request);
        Assertions.assertEquals("Test", response.password);

        Utils.teardown();
    }
}
