package io.firstpass.ipc.models.response;

import java.util.ArrayList;

public class BaseIPCResponse {
    public Object data;
    public IPCErrorResponse error;

    public BaseIPCResponse(Object object) {
        this.data = object;
    }

    public BaseIPCResponse() {}

}
