package io.firstpass;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.firstpass.ipc.exceptions.IPCException;
import io.firstpass.ipc.models.request.BaseFrontendRequest;
import io.firstpass.ipc.models.request.MasterpasswordRequest;
import io.firstpass.ipc.models.response.MasterpasswordResponse;
import io.firstpass.ipc.parser.MessageParser;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class FirstPass {

    public static void main(String[] args) {
        MessageParser messageParser = new MessageParser();
        messageParser.addMessageListener("masterpassword", new MasterpasswordRequest(), new MasterpasswordResponse(), (data) -> {
            return new MasterpasswordResponse();
        });
        System.out.println(messageParser.onMessage("{\"type\":\"masterpassword\",\"data\":{\"password\":\"test\"}}"));
    }

}
