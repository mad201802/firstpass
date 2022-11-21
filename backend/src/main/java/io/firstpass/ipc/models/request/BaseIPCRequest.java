package io.firstpass.ipc.models.request;

import com.google.gson.JsonObject;

public class BaseIPCRequest {
    public String type;
    public JsonObject data;
}
