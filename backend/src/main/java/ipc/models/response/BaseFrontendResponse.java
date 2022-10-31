package ipc.models.response;

import java.util.ArrayList;

public class BaseFrontendResponse<T> {
    public int status;
    public String message;
    public T data;
    public ArrayList<String> errors;
}
