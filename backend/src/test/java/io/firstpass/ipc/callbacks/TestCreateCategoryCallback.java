package io.firstpass.ipc.callbacks;

import io.firstpass.ipc.communication.request.CreateCategoryRequest;
import io.firstpass.ipc.communication.response.CreateCategoryResponse;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Utils;

public class TestCreateCategoryCallback {
    @Test()
    public void test_create_category_callback() throws IPCException {
        Utils.initPasswordManagerInstance();

        CreateCategoryRequest request = new CreateCategoryRequest();
        request.name = "test";

        CreateCategoryResponse response = CreateCategoryCallback.call(request);
        Assertions.assertEquals(2, response.id);
        Assertions.assertEquals("test", response.name);

        Utils.teardown();
    }
}
