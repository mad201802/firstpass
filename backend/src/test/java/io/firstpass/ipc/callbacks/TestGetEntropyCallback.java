package io.firstpass.ipc.callbacks;

import io.firstpass.ipc.communication.request.GetEntropyRequest;
import io.firstpass.ipc.communication.response.GetEntropyResponse;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestGetEntropyCallback {
    @Test()
    public void test_get_entropy_callback() throws IPCException {
        Utils.initStrengthAnalyzerInstance();

        GetEntropyRequest request = new GetEntropyRequest();
        request.passwordToCompute = "test";

        GetEntropyResponse response = GetEntropyCallback.call(request);
        Assertions.assertEquals(19.43192398051029, response.entropy, 0.1);
    }
}
