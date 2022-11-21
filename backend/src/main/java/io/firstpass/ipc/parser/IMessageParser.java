package io.firstpass.ipc.parser;

import io.firstpass.ipc.interfaces.IOnMessageRecieve;

public interface IMessageParser {
    String onMessage(String message);
    <T, U> void addMessageListener(String type, T requestClass, U responseClass, IOnMessageRecieve<T, U> onMessage);
}
