package io.firstpass.ipc.parser;

import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.ipc.parser.messages.TestMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestMessageParser {

    @Test()
    public void test_message_parser_correct() {
        IMessageParser parser = new MessageParser();
        AtomicBoolean executed = new AtomicBoolean(false);
        parser.addMessageListener("test", TestMessage.class, TestMessage.class, (data) -> {
            executed.set(true);
            return data;
        });
        String response = parser.onMessage("{\"type\": \"test\", \"data\":{\"data\": \"itworks\"}}");
        Assertions.assertTrue(executed.get());
        Assertions.assertEquals(response , "{\"data\":{\"data\":\"itworks\"}}");
    }

    @Test()
    public void test_message_parser_wrong_type() {
        IMessageParser parser = new MessageParser();
        AtomicBoolean executed = new AtomicBoolean(false);
        parser.addMessageListener("test", TestMessage.class, TestMessage.class, (data) -> {
            executed.set(true);
            return data;
        });
        String response = parser.onMessage("{\"type\": \"nottest\", \"data\":{\"data\": \"itworks\"}}");
        Assertions.assertFalse(executed.get());
        Assertions.assertEquals(response, "{\"error\":{\"code\":404,\"message\":\"Message type not found\"}}");
    }

    @Test()
    public void test_message_parser_wrong_message_format() {
        IMessageParser parser = new MessageParser();
        AtomicBoolean executed = new AtomicBoolean(false);
        parser.addMessageListener("test", TestMessage.class, TestMessage.class, (data) -> {
            executed.set(true);
            return data;
        });
        String response = parser.onMessage("{\"type\": \"test\", \"data\":{\"notdata\": \"itworks\"}}");
        Assertions.assertTrue(executed.get());
        Assertions.assertEquals(response, "{\"data\":{}}");
    }

    @Test()
    public void test_message_parser_throws_error() {
        IMessageParser parser = new MessageParser();
        parser.addMessageListener("test", TestMessage.class, TestMessage.class, (TestMessage data) -> {
            throw new IPCException(400, "Oh crap!");
        });
        String response = parser.onMessage("{\"type\": \"test\", \"data\":{\"data\": \"itworks\"}}");
        Assertions.assertEquals(response, "{\"error\":{\"code\":400,\"message\":\"Oh crap!\"}}");
    }

    @Test()
    public void test_message_parser_invalid_json() {
        IMessageParser parser = new MessageParser();
        parser.addMessageListener("test", TestMessage.class, TestMessage.class, (TestMessage data) -> data);
        String response = parser.onMessage("{\"type\": \"test\", \"data\":{\"data\": \"itworks\"}");
        Assertions.assertEquals(response, "{\"error\":{\"code\":500,\"message\":\"Invalid JSON\"}}");
    }

    @Test()
    public void test_message_parser_invalid_data() {
        IMessageParser parser = new MessageParser();
        parser.addMessageListener("test", TestMessage.class, TestMessage.class, (TestMessage data) -> data);
        String response = parser.onMessage("{\"type\": \"test\", \"datas\":{\"data\": \"itworks\"}}");
        Assertions.assertEquals(response, "{\"error\":{\"code\":500,\"message\":\"Json has wrong data\"}}");
    }

}
