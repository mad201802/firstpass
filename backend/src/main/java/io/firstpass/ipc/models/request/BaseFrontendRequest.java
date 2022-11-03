package io.firstpass.ipc.models.request;

public class BaseFrontendRequest<T> {
    public String type;
    public T data;
}
