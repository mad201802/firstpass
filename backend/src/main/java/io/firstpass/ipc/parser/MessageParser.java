package io.firstpass.ipc.parser;

import com.google.gson.Gson;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.ipc.interfaces.IOnMessageRecieve;
import io.firstpass.ipc.communication.ClassMessageObject;
import io.firstpass.ipc.communication.request.BaseIPCRequest;
import io.firstpass.ipc.communication.response.BaseIPCResponse;
import io.firstpass.ipc.communication.response.IPCErrorResponse;

import java.util.HashMap;

public class MessageParser implements IMessageParser {
    private final Gson gson;
    private final HashMap<String, ClassMessageObject> onMessageHashMap;

    /**
     * This method is used to construct a new message parser
     */
    public MessageParser() {
        this.gson = new Gson();
        this.onMessageHashMap = new HashMap<>();
    }

    /**
     * This method is used to parse a message
     * @param message The message to parse
     * @return The response message
     */
    public String onMessage(String message) {
        try {
            BaseIPCRequest request = this.parseRequest(message);
            if(!this.onMessageHashMap.containsKey(request.type))
                throw new IPCException(404, "Message type not found");
            ClassMessageObject classMessageObject = this.onMessageHashMap.get(request.type);
            Object data = this.gson.fromJson(request.data.toString(), classMessageObject.requestClass);
            return gson.toJson(new BaseIPCResponse(classMessageObject.onMessage.call(data)));
        } catch (NullPointerException ex) {
            return this.constructErrorMessage(500, ex.getMessage());
        } catch (IllegalStateException ex) {
            return this.constructErrorMessage(500, "Invalid JSON");
        } catch (IPCException ex) {
            return this.constructErrorMessage(ex.getStatus(), ex.getError());
        }
    }

    /**
     * Add a new message listener to the parser
     * @param type The type of message to listen for
     * @param requestClass The request class
     * @param responseClass The response class
     * @param onMessage The callback function
     * @param <T> The request class
     * @param <U> The response class
     */
    public <T, U> void addMessageListener(String type, Class<T> requestClass, Class<U> responseClass, IOnMessageRecieve<T, U> onMessage) {
        this.onMessageHashMap.put(type, new ClassMessageObject(requestClass, responseClass, onMessage));
    }

    /**
     * This method is used to construct an error message
     * @param code The error code
     * @param message The error message
     * @return The error message
     */
    private String constructErrorMessage(int code, String message) {
        IPCErrorResponse error = new IPCErrorResponse();
        error.code = code;
        error.message = message;

        BaseIPCResponse response = new BaseIPCResponse();
        response.error = error;
        response.data = null;
        return this.gson.toJson(response);
    }

    /**
     * This method is used to parse a request
     * @param message The message to parse
     * @return The parsed request
     */
    private BaseIPCRequest parseRequest(String message) {
        try {
            return this.gson.fromJson(message, BaseIPCRequest.class);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid message format");
        }
    }
}
