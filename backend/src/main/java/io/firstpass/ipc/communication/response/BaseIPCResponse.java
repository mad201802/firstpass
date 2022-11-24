package io.firstpass.ipc.communication.response;

public class BaseIPCResponse {
    public Object data;
    public IPCErrorResponse error;

    public BaseIPCResponse(Object object) {
        this.data = object;
    }

    public BaseIPCResponse() {}

}
