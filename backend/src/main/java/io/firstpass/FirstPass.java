package io.firstpass;

import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.ipc.models.request.MasterpasswordRequest;
import io.firstpass.ipc.models.response.MasterpasswordResponse;
import io.firstpass.ipc.parser.MessageParser;

public class FirstPass {

    public static void main(String[] args) {
        MessageParser messageParser = new MessageParser();
        messageParser.addMessageListener("masterpassword", MasterpasswordRequest.class, MasterpasswordResponse.class, (data) -> {
            throw new IPCException(500, "Test");
        });
        System.out.println(messageParser.onMessage("{\"type\":\"masterpassword\",\"data\":{\"password\":\"test\"}}"));
    }

}
