package io.firstpass;

import io.firstpass.config.Configuration;
import io.firstpass.config.schemas.DefaultConfig;
import io.firstpass.ipc.callbacks.CreateDatabaseCallback;
import io.firstpass.ipc.callbacks.CreateEntryCallback;
import io.firstpass.ipc.callbacks.GetLoadedDBsCallback;
import io.firstpass.ipc.callbacks.LoadDatabaseCallback;
import io.firstpass.ipc.communication.request.CreateDatabaseRequest;
import io.firstpass.ipc.communication.request.CreateEntryRequest;
import io.firstpass.ipc.communication.request.EmtpyRequest;
import io.firstpass.ipc.communication.request.LoadDatabaseRequest;
import io.firstpass.ipc.communication.response.CreateEntryResponse;
import io.firstpass.ipc.communication.response.GetLoadedDBsResponse;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.handler.IPCHandler;
import io.firstpass.ipc.parser.MessageParser;
import io.firstpass.manager.PasswordManager;

public class FirstPass {
    public static PasswordManager passwordManager;
    public static Configuration<DefaultConfig> configuration;

    public static void main(String[] args) {
        System.out.println(System.getenv("APPDATA") + "\\firstpass");
        configuration = new Configuration<DefaultConfig>( new DefaultConfig(), System.getenv("APPDATA") + "\\firstpass", "firstpass_conf", false);
        configuration.initConfig();
        
        IPCHandler ipcHandler = new IPCHandler(System.in, System.out);

        MessageParser messageParser = new MessageParser();
        messageParser.addMessageListener("CREATE_DB", CreateDatabaseRequest.class, OpenDatabaseResponse.class, CreateDatabaseCallback::call);
        messageParser.addMessageListener("OPEN_DB", LoadDatabaseRequest.class, OpenDatabaseResponse.class, LoadDatabaseCallback::call);

        messageParser.addMessageListener("LIST_DBS", EmtpyRequest.class, GetLoadedDBsResponse.class, GetLoadedDBsCallback::call);

        messageParser.addMessageListener("CREATE_ENTRY", CreateEntryRequest.class, CreateEntryResponse.class, CreateEntryCallback::call);

        while (true) {
            String message = ipcHandler.readLine();
            ipcHandler.writeLine(messageParser.onMessage(message));
        }

    }

}

