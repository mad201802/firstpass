package io.firstpass.ipc.models;

import io.firstpass.ipc.interfaces.IOnMessageRecieve;

public class ClassMessageObject<T, U> {
    public T requestClass;

    public U responseClass;
    public IOnMessageRecieve<T, U> onMessage;

    public ClassMessageObject(T requestClass, U responseClass, IOnMessageRecieve<T, U> onMessage) {
        this.requestClass = requestClass;
        this.responseClass = responseClass;
        this.onMessage = onMessage;
    }

}
