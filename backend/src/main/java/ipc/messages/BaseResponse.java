package ipc.messages;

import java.util.List;

public class BaseResponse<T> {
    public int status;
    public String message;
    public T data;
    public List<String> errors;
}
