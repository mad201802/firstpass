package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.DeleteCategoryRequest;
import io.firstpass.ipc.communication.response.SimpleStatusResponse;
import io.firstpass.ipc.exceptions.IPCException;

import java.util.Objects;

public class DeleteCategoryCallback {
    public static SimpleStatusResponse call(DeleteCategoryRequest request) throws IPCException {
        if (FirstPass.passwordManager == null)
            throw new IPCException(404, "No database is open");

        if(FirstPass.passwordManager.getAllCategories().stream().findAny().filter(category -> category.getId() == request.id).isEmpty())
            throw new IPCException(400, "Category does not exist");

        if(request.id == 1)
            throw new IPCException(400, "Cannot delete default category");

        if(FirstPass.passwordManager.deleteCategory(request.id, false))
            return new SimpleStatusResponse();
        else
            throw new IPCException(500, "Failed to delete category");
    }
}
