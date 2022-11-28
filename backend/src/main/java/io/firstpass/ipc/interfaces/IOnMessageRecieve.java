package io.firstpass.ipc.interfaces;

import io.firstpass.ipc.exceptions.IPCException;

public interface IOnMessageRecieve<T, U> {
    U call(T data) throws IPCException;
}
