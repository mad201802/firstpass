package io.firstpass.ipc.parser;

import io.firstpass.ipc.interfaces.IOnMessageRecieve;

/**
 * This interface is used to define a message parser
 */
public interface IMessageParser {

    /**
     * This method is used to parse a incoming message from the IPC stream
     * @param message The message to parse
     * @return The response message
     */
    String onMessage(String message);

    /**
     * This method is used to add a message listener
     * @param type The type of message to listen for
     * @param requestClass The request class
     * @param responseClass The response class
     * @param onMessage The callback function
     * @param <T> The request class
     * @param <U> The response class
     */
    <T, U> void addMessageListener(String type, Class<T> requestClass, Class<U> responseClass, IOnMessageRecieve<T, U> onMessage);
}
