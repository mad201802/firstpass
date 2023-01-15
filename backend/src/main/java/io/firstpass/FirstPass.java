package io.firstpass;

import io.firstpass.config.Configuration;
import io.firstpass.config.schemas.DefaultConfig;
import io.firstpass.ipc.callbacks.*;
import io.firstpass.ipc.communication.request.CreateDatabaseRequest;
import io.firstpass.ipc.communication.request.CreateEntryRequest;
import io.firstpass.ipc.communication.request.EmtpyRequest;
import io.firstpass.ipc.communication.request.EmptyRequest;
import io.firstpass.ipc.communication.request.LoadDatabaseRequest;
import io.firstpass.ipc.communication.response.CreateEntryResponse;
import io.firstpass.ipc.communication.response.ListRecentDBsResponse;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.communication.response.SimpleStatusResponse;
import io.firstpass.ipc.handler.IPCHandler;
import io.firstpass.ipc.parser.MessageParser;
import io.firstpass.manager.PasswordManager;

public class FirstPass {
    public static PasswordManager passwordManager;
    public static Configuration<DefaultConfig> configuration;

    public static void main(String[] args) {
        configuration = new Configuration<>( new DefaultConfig(), "firstpass_conf", false);

        try {
            configuration.initConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        IPCHandler ipcHandler = new IPCHandler(System.in, System.out);

        MessageParser messageParser = new MessageParser();
        messageParser.addMessageListener("CREATE_DB", CreateDatabaseRequest.class, OpenDatabaseResponse.class, CreateDatabaseCallback::call);
        messageParser.addMessageListener("OPEN_DB", LoadDatabaseRequest.class, OpenDatabaseResponse.class, LoadDatabaseCallback::call);
        messageParser.addMessageListener("CLOSE_DB", EmptyRequest.class, SimpleStatusResponse.class, CloseDatabaseCallback::call);


        messageParser.addMessageListener("LIST_RECENT_DBS", EmtpyRequest.class, ListRecentDBsResponse.class, ListRecentDBsCallback::call);

        messageParser.addMessageListener("CREATE_ENTRY", CreateEntryRequest.class, CreateEntryResponse.class, CreateEntryCallback::call);

        while (true) {
            String message = ipcHandler.readLine();
            ipcHandler.writeLine(messageParser.onMessage(message));
        }

    }

}