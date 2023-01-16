package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.GetEntropyRequest;
import io.firstpass.ipc.communication.response.GetEntropyResponse;
import io.firstpass.ipc.exceptions.IPCException;

public class GetEntropyCallback {
    public static GetEntropyResponse call(GetEntropyRequest request) throws IPCException {
        GetEntropyResponse response = new GetEntropyResponse();
        response.entropy = FirstPass.strengthAnalyzer.computeEntropy(request.passwordToCompute);
        return response;
    }
}
