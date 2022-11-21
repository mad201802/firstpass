package io.firstpass.ipc.handler;

import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.ipc.handler.interfaces.IHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class TestIPCHandler {

    @Test
    public void test_handler_message_read() {
        String message = "hello";
        InputStream inputStream = new ByteArrayInputStream(message.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();
        IHandler handler = new IPCHandler(inputStream, outputStream);
        Assertions.assertNotNull(handler);
        Assertions.assertEquals(message, handler.readLine());
    }

    @Test
    public void test_handler_message_write() {
        String message = "hello";
        InputStream inputStream = new ByteArrayInputStream(message.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream(256);
        IHandler handler = new IPCHandler(inputStream, outputStream);
        Assertions.assertNotNull(handler);
        handler.writeLine(message);
        Assertions.assertEquals(message, outputStream.toString().strip());
    }

}
