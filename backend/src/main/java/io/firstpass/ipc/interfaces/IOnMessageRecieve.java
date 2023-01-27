package io.firstpass.ipc.interfaces;

import io.firstpass.ipc.exceptions.IPCException;

/**
 * This interface is used to define a callback function
 * @param <T> The request class
 * @param <U> The response class
 */
public interface IOnMessageRecieve<T, U> {

    /**
     * This method is used to define a callback function
     * @param data The data to process
     * @return The response data
     * @throws IPCException An exception thrown when an error occurs
     */
    U call(T data) throws IPCException;
}
