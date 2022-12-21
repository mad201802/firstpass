package io.firstpass.utils;

import io.firstpass.ipc.parser.IMessageParser;
import io.firstpass.ipc.parser.MessageParser;
import io.firstpass.ipc.parser.messages.TestMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestUtils {
    @Test()
    public void test_bytes_to_base64() {
        // Write a test for the bytesToBase64 method
        String testString = "Hello World!";
        String expected = "SGVsbG8gV29ybGQh";
        String actual = Utils.bytesToBase64(testString.getBytes());
        Assertions.assertEquals(expected, actual);
    }

    @Test()
    public void test_base64_to_bytes() {
        // Write a test for the base64ToBytes method
        String testString = "SGVsbG8gV29ybGQh";
        String expected = "Hello World!";
        String actual = new String(Utils.base64ToBytes(testString));
        Assertions.assertEquals(expected, actual);
    }

    @Test()
    public void test_bytes_to_hex() {
        // Write a test for the bytesToHex method
        String testString = "Hello World!";
        String expected = "48656c6c6f20576f726c6421";
        String actual = Utils.bytesToHex(testString.getBytes());
        Assertions.assertEquals(expected, actual);
    }
}
