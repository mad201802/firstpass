package io.firstpass.ipc.callbacks;

import io.firstpass.ipc.communication.request.CreateDatabaseRequest;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

public class TestCreateDatabaseCallback {

    private static String filename = "test.fpdb";

//    public void test_successfully_create_database() {
//        CreateDatabaseRequest request = new CreateDatabaseRequest();
//        request.filepath = filename;
//        request.masterpassword = "password";
//
//        try {
//            OpenDatabaseResponse response = CreateDatabaseCallback.call(request);
//            Assertions.assertNotNull(response);
//            Assertions.assertEquals(0, response.entries.size());
//            Assertions.assertEquals(8, response.categories.size());
//        } catch (IPCException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @AfterAll()
    public static void after() throws Exception {
        File file = new File(filename);
        Files.deleteIfExists(file.toPath());
    }

}
