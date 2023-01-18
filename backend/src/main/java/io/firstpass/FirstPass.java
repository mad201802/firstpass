package io.firstpass;

import io.firstpass.config.Configuration;
import io.firstpass.config.schemas.DefaultConfig;
import io.firstpass.ipc.callbacks.*;
import io.firstpass.ipc.communication.request.*;
import io.firstpass.ipc.communication.response.*;
import io.firstpass.ipc.handler.IPCHandler;
import io.firstpass.ipc.parser.MessageParser;
import io.firstpass.logic.StrengthAnalyzer;
import io.firstpass.manager.PasswordManager;
import io.firstpass.utils.Utils;

import java.util.HashMap;

public class FirstPass {
    public static PasswordManager passwordManager;
    public static Configuration<DefaultConfig> configuration;
    public static StrengthAnalyzer strengthAnalyzer;

    public static void main(String[] args) {

        // Disable Logging when run by electron app
        if (args.length != 0 && args[0].equals("--no-logging")) Utils.useLogging = false;

        configuration = new Configuration<>( new DefaultConfig(), "firstpass_conf", false);
        strengthAnalyzer = new StrengthAnalyzer();

        try {
            configuration.initConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        IPCHandler ipcHandler = new IPCHandler(System.in, System.out);

        MessageParser messageParser = new MessageParser();
        messageParser.addMessageListener("CREATE_DB", CreateDatabaseRequest.class, OpenDatabaseResponse.class, CreateDatabaseCallback::call);
        messageParser.addMessageListener("OPEN_DB", LoadDatabaseRequest.class, OpenDatabaseResponse.class, LoadDatabaseCallback::call);
        messageParser.addMessageListener("CLOSE_DB", EmtpyRequest.class, SimpleStatusResponse.class, CloseDatabaseCallback::call);
        messageParser.addMessageListener("LIST_RECENT_DBS", EmtpyRequest.class, ListRecentDBsResponse.class, ListRecentDBsCallback::call);

        messageParser.addMessageListener("CREATE_ENTRY", CreateEntryRequest.class, CreateEntryResponse.class, CreateEntryCallback::call);
        messageParser.addMessageListener("UPDATE_ENTRY", UpdateEntryRequest.class, CreateEntryResponse.class, UpdateEntryCallback::call);
        messageParser.addMessageListener("GET_PASSWORD", GetPasswordRequest.class, GetPasswordResponse.class, GetPasswordCallback::call);

        messageParser.addMessageListener("CREATE_CATEGORY", CreateCategoryRequest.class, CreateCategoryResponse.class, CreateCategoryCallback::call);
        messageParser.addMessageListener("DELETE_CATEGORY", DeleteCategoryRequest.class, SimpleStatusResponse.class, DeleteCategoryCallback::call);

        messageParser.addMessageListener("GET_ENTROPY", GetEntropyRequest.class, GetEntropyResponse.class, GetEntropyCallback::call);
        messageParser.addMessageListener("QUERY_DB", QueryDatabaseRequest.class, HashMap.class, QueryDatabaseCallback::call);

        while (true) {
            String message = ipcHandler.readLine();
            ipcHandler.writeLine(messageParser.onMessage(message));
        }

    }

}