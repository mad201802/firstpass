package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.CreateCategoryRequest;
import io.firstpass.ipc.communication.response.CreateCategoryResponse;
import io.firstpass.ipc.exceptions.IPCException;

import java.util.Objects;

public class CreateCategoryCallback {
    public static CreateCategoryResponse call(CreateCategoryRequest request) throws IPCException{
        if(FirstPass.passwordManager == null)
            throw new IPCException(400, "Database not loaded");

        if(FirstPass.passwordManager.getAllCategories().stream().findAny().filter(category -> Objects.equals(category.getCategory(), request.name)).isPresent())
            throw new IPCException(400, "Category already exists");

        int id = FirstPass.passwordManager.addCategory(request.name);
        if (id == -1)
            throw new IPCException(500, "Failed to create category");

        CreateCategoryResponse response = new CreateCategoryResponse();
        response.id = id;
        response.name = request.name;
        return response;
    }
}
