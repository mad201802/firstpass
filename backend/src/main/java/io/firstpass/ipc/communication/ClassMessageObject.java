package io.firstpass.ipc.communication;

import io.firstpass.ipc.interfaces.IOnMessageRecieve;

/**
 * This class maps a request and response class to a callback function
 * @param <T>
 * @param <U>
 */
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
