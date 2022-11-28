package io.firstpass.ipc.communication.request;

import com.google.gson.JsonObject;

public class BaseIPCRequest {
    public String type;
    public JsonObject data;
}
