package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.UpdateCategoryRequest;
import io.firstpass.ipc.communication.response.UpdateCategoryResponse;
import io.firstpass.ipc.exceptions.IPCException;

import java.util.Objects;

public class UpdateCategoryCallback {
    public static UpdateCategoryResponse call(UpdateCategoryRequest request) throws IPCException{
        if(FirstPass.passwordManager == null)
            throw new IPCException(503, "Database not loaded");

        if(FirstPass.passwordManager.getAllCategories().stream().findAny().filter(category -> Objects.equals(category.getCategory(), request.new_name)).isPresent())
            throw new IPCException(404, "Category already exists");

        request.new_name = request.new_name.trim();

        if(request.new_name.isEmpty())
            throw new IPCException(400, "Category name cannot be empty");

        int id = FirstPass.passwordManager.updateCategory(request.id, request.new_name);


        UpdateCategoryResponse response = new UpdateCategoryResponse();
        response.id = id;
        response.new_name = request.new_name;
        return response;
    }
}
