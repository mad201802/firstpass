package io.firstpass.ipc.interfaces;

import com.google.gson.JsonObject;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.ipc.models.response.BaseFrontendResponse;

public interface IOnMessageRecieve<T, U> {
    U call(T data) throws IPCException;
}
