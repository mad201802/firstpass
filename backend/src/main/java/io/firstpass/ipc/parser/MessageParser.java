package io.firstpass.ipc.parser;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.ipc.interfaces.IOnMessageRecieve;
import io.firstpass.ipc.models.ClassMessageObject;
import io.firstpass.ipc.models.request.BaseFrontendRequest;
import io.firstpass.ipc.models.response.BaseFrontendResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageParser implements IMessageParser {
    private final Gson gson;
    private final HashMap<String, ClassMessageObject> onMessageHashMap;

    public MessageParser() {
        this.gson = new Gson();
        this.onMessageHashMap = new HashMap<>();
    }

    public String onMessage(String message) {
        try {
            BaseFrontendRequest request = this.parseRequest(message);
            if(!this.onMessageHashMap.containsKey(request.type))
                throw new IPCException(404, "Message type not found");
            ClassMessageObject classMessageObject = this.onMessageHashMap.get(request.type);
            Object data = this.gson.fromJson(request.data.toString(), classMessageObject.requestClass.getClass());
            return gson.toJson(new BaseFrontendResponse(classMessageObject.onMessage.call(data)));
        } catch (JsonSyntaxException ex) {
            return this.constructErrorMessage(500, "Json has wrong data");
        } catch (IllegalStateException ex) {
            return this.constructErrorMessage(500, "Invalid JSON");
        } catch (IPCException ex) {
            return this.constructErrorMessage(ex.getStatus(), ex.getErrors());
        }
    }

    public <T, U> void addMessageListener(String type, T requestClass, U responseClass, IOnMessageRecieve<T, U> onMessage) {
        this.onMessageHashMap.put(type, new ClassMessageObject(requestClass, responseClass, onMessage));
    }

    private String constructErrorMessage(int status, String message) {
        BaseFrontendResponse response = new BaseFrontendResponse();
        response.status = status;
        response.errors.add(message);
        response.data = null;
        return this.gson.toJson(response);
    }

    private String constructErrorMessage(int status, ArrayList<String> errors) {
        BaseFrontendResponse response = new BaseFrontendResponse();
        response.status = status;
        response.errors = errors;
        response.data = null;
        return this.gson.toJson(response);
    }

    private BaseFrontendRequest parseRequest(String message) {
        try {
            return this.gson.fromJson(message, BaseFrontendRequest.class);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid message format");
        }
    }
}
