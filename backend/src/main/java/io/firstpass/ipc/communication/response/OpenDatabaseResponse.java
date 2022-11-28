package io.firstpass.ipc.communication.response;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.ipc.models.EntryModel;

import java.util.ArrayList;

public class OpenDatabaseResponse {
    public ArrayList<CategoryModel> categories;
    public ArrayList<EntryModel> entries;
}
