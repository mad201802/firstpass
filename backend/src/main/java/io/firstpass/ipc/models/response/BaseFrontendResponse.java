package io.firstpass.ipc.models.response;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class BaseFrontendResponse {
    public int status = 200;

    public Object data = new Object();
    public ArrayList<String> errors = new ArrayList<>();

    public BaseFrontendResponse(Object object) {
        this.data = object;
    }

    public BaseFrontendResponse() {}

}
