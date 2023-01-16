package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.CreateEntryRequest;
import io.firstpass.ipc.communication.request.LoadDatabaseRequest;
import io.firstpass.ipc.communication.response.CreateEntryResponse;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.exceptions.IPCException;

import java.util.Collections;

public class CreateEntryCallback {
    public static CreateEntryResponse call(CreateEntryRequest request) throws IPCException {
        System.out.println(request.category_id);
        if(FirstPass.passwordManager == null)
            throw new IPCException(400, "Database not loaded");

        System.out.println(FirstPass.passwordManager.getAllCategories().stream().findAny().filter(category -> category.getId() == request.category_id).stream().toArray());
        if(FirstPass.passwordManager.getAllCategories().stream().findAny().filter(category -> category.getId() == request.category_id).isEmpty())
            throw new IPCException(400, "Category not found");

        int id = FirstPass.passwordManager.addEntry(request.name, request.username, request.password, request.category_id, request.url, request.notes);

        CreateEntryResponse response = new CreateEntryResponse();
        response.id = id;
        response.name = request.name;
        response.username = request.username;
        response.password = String.join("", Collections.nCopies(request.password.length(), "*"));

        return response;
    }
}
