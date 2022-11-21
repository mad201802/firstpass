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
        parser.addMessageListener("test", new TestMessage(), new TestMessage(), (data) -> {
            executed.set(true);
            return data;
        });
        String response = parser.onMessage("{\"type\": \"test\", \"data\":{\"data\": \"itworks\"}}");
        Assertions.assertTrue(executed.get());
        Assertions.assertEquals(response , "{\"status\":200,\"data\":{\"data\":\"itworks\"},\"errors\":[]}");
    }

    @Test()
    public void test_message_parser_wrong_type() {
        IMessageParser parser = new MessageParser();
        AtomicBoolean executed = new AtomicBoolean(false);
        parser.addMessageListener("test", new TestMessage(), new TestMessage(), (data) -> {
            executed.set(true);
            return data;
        });
        String response = parser.onMessage("{\"type\": \"nottest\", \"data\":{\"data\": \"itworks\"}}");
        Assertions.assertFalse(executed.get());
        Assertions.assertEquals(response, "{\"status\":404,\"errors\":[\"Message type not found\"]}");
    }

    @Test()
    public void test_message_parser_wrong_message_format() {
        IMessageParser parser = new MessageParser();
        AtomicBoolean executed = new AtomicBoolean(false);
        parser.addMessageListener("test", new TestMessage(), new TestMessage(), (TestMessage data) -> {
            executed.set(true);
            return data.data;
        });
        String response = parser.onMessage("{\"type\": \"test\", \"data\":{\"notdata\": \"itworks\"}}");
        Assertions.assertTrue(executed.get());
        Assertions.assertEquals(response, "{\"status\":200,\"errors\":[]}");
    }

    @Test()
    public void test_message_parser_throws_error() {
        IMessageParser parser = new MessageParser();
        parser.addMessageListener("test", new TestMessage(), new TestMessage(), (TestMessage data) -> {
            throw new IPCException(400, "Oh crap!");
        });
        String response = parser.onMessage("{\"type\": \"test\", \"data\":{\"data\": \"itworks\"}}");
        Assertions.assertEquals(response, "{\"status\":400,\"errors\":[\"Oh crap!\"]}");
    }

}
