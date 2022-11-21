package io.firstpass.ipc.models;

import io.firstpass.ipc.interfaces.IOnMessageRecieve;

public class ClassMessageObject<T, U> {
    public Class<T> requestClass;

    public Class<U> responseClass;
    public IOnMessageRecieve<T, U> onMessage;

    public ClassMessageObject(Class<T> requestClass, Class<U> responseClass, IOnMessageRecieve<T, U> onMessage) {
        this.requestClass = requestClass;
        this.responseClass = responseClass;
        this.onMessage = onMessage;
    }

}
