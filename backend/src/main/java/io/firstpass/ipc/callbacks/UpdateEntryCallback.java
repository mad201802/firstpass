package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.UpdateEntryRequest;
import io.firstpass.ipc.communication.response.CreateEntryResponse;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.manager.models.EntryModel;

import java.util.Collections;

public class UpdateEntryCallback {
    public static CreateEntryResponse call(UpdateEntryRequest request) throws IPCException{
        if(FirstPass.passwordManager == null)
            throw new IPCException(400, "Database not loaded");
        // Check if category exists
        if(FirstPass.passwordManager.getAllCategories().stream().filter(category -> category.getId() == request.category_id).findAny().isEmpty() && request.category_id != -1)
            throw new IPCException(400, "Category does not exist");

        EntryModel updated = FirstPass.passwordManager.updateEntry(request.id, request.name, request.username, request.password, request.category_id, request.url, request.notes);
        if(updated == null)
            throw new IPCException(400, "Entry does not exist");

        CreateEntryResponse response = new CreateEntryResponse();
        response.id = updated.getId();
        response.url = updated.getUrl();
        response.name = updated.getName();
        response.username = updated.getUsername();
        response.password = String.join("", Collections.nCopies(updated.getPassword().length(), "*"));
        response.category_id = updated.getCategory();
        response.notes = updated.getNotes();

        return response;
    }
}
