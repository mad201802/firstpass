package io.firstpass.ipc.callbacks;

import io.firstpass.FirstPass;
import io.firstpass.ipc.communication.request.UpdateCategoryRequest;
import io.firstpass.ipc.communication.response.UpdateCategoryResponse;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestUpdateCategoryCallback {

    @Test()
    public void test_update_category_callback() throws IPCException {
        Utils.initPasswordManagerInstance();
        int category_id = FirstPass.passwordManager.createCategory("Test");

        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.id = category_id;
        request.new_name = "Test2";

        UpdateCategoryResponse response = UpdateCategoryCallback.call(request);

        Assertions.assertEquals(category_id, response.id);
        Assertions.assertEquals("Test2", response.new_name);

        Utils.teardown();
    }
}
