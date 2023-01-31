package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.CreateCategoryRequest;
import io.firstpass.ipc.communication.response.CreateCategoryResponse;
import io.firstpass.ipc.exceptions.IPCException;

import java.util.Objects;

public class CreateCategoryCallback {
    public static CreateCategoryResponse call(CreateCategoryRequest request) throws IPCException{
        if(FirstPass.passwordManager == null)
            throw new IPCException(503, "Database not loaded");

        if(FirstPass.passwordManager.getAllCategories().stream().findAny().filter(category -> Objects.equals(category.getCategory(), request.name)).isPresent())
            throw new IPCException(409, "Category already exists");

        request.name = request.name.trim();

        if(request.name.isEmpty())
            throw new IPCException(400, "Category name cannot be empty");

        int id = FirstPass.passwordManager.createCategory(request.name);
        if (id == -1)
            throw new IPCException(500, "Failed to create category");

        CreateCategoryResponse response = new CreateCategoryResponse();
        response.id = id;
        response.name = request.name;
        return response;
    }
}
