package io.firstpass;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.ipc.communication.request.OpenDatabaseRequest;
import io.firstpass.ipc.communication.response.OpenDatabaseResponse;
import io.firstpass.ipc.handler.IPCHandler;
import io.firstpass.ipc.models.EntryModel;
import io.firstpass.ipc.parser.MessageParser;

import java.util.ArrayList;

public class FirstPass {

    public static void main(String[] args) {
        IPCHandler ipcHandler = new IPCHandler(System.in, System.out);

        MessageParser messageParser = new MessageParser();
        messageParser.addMessageListener("OPEN_DB", OpenDatabaseRequest.class, OpenDatabaseResponse.class, (OpenDatabaseRequest data) -> {
            OpenDatabaseResponse response = new OpenDatabaseResponse();
            response.categories = new ArrayList<>();

            CategoryModel internetCategory = new CategoryModel();
            internetCategory.id = 0;
            internetCategory.category = "Internet";

            CategoryModel streamingCategory = new CategoryModel();
            streamingCategory.id = 1;
            streamingCategory.category = "Internet";

            response.categories.add(internetCategory);
            response.categories.add(streamingCategory);

            response.entries = new ArrayList<>();

            EntryModel firstEntry = new EntryModel();
            firstEntry.id = 0;
            firstEntry.category_id = 0;
            firstEntry.name = "Google";
            firstEntry.username = "james";
            response.entries.add(firstEntry);

            EntryModel secondEntry = new EntryModel();
            secondEntry.id = 1;
            secondEntry.category_id = 1;
            secondEntry.name = "Netflix";
            secondEntry.username = "james@example.com";
            response.entries.add(secondEntry);

            return response;
        });

        while (true) {
            String message = ipcHandler.readLine();
            ipcHandler.writeLine(messageParser.onMessage(message));
        }

    }

}
