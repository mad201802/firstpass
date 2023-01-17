package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.CreateEntryRequest;
import io.firstpass.ipc.communication.response.CreateEntryResponse;
import io.firstpass.ipc.exceptions.IPCException;

import java.util.Collections;

public class CreateEntryCallback {
    public static CreateEntryResponse call(CreateEntryRequest request) throws IPCException {
        if(FirstPass.passwordManager == null)
            throw new IPCException(400, "Database not loaded");

        // Check if category exists
        if(FirstPass.passwordManager.getAllCategories().stream().filter(category -> category.getId() == request.category_id).findAny().isEmpty())
            throw new IPCException(400, "Category does not exist");

        int id = FirstPass.passwordManager.createEntry(request.name, request.username, request.password, request.category_id, request.url, request.notes);

        CreateEntryResponse response = new CreateEntryResponse();
        response.id = id;
        response.url = request.url;
        response.name = request.name;
        response.username = request.username;
        response.password = String.join("", Collections.nCopies(request.password.length(), "*"));
        response.category_id = request.category_id;
        response.notes = request.notes;

        return response;
    }
}
