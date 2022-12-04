package io.firstpass.ipc.communication.response;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.manager.models.EntryModel;

import java.util.ArrayList;

public class DatabaseOpenResponse {
    public ArrayList<CategoryModel> categories;
    public ArrayList<EntryModel> entries;
}
