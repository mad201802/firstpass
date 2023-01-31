package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.DeleteCategoryRequest;
import io.firstpass.ipc.communication.response.SimpleStatusResponse;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestDeleteCategoryCallback {
    @Test()
    public void test_delete_category_callback() throws IPCException {
        Utils.initPasswordManagerInstance();
        FirstPass.passwordManager.createCategory("test");

        DeleteCategoryRequest request = new DeleteCategoryRequest();
        request.id = 2;

        SimpleStatusResponse response = DeleteCategoryCallback.call(request);
        Assertions.assertEquals(200, response.status);

        Utils.teardown();
    }
}
