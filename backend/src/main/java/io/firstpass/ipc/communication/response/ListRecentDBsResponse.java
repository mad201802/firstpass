package io.firstpass.ipc.communication.response;

import io.firstpass.config.schemas.LoadedDB;

import java.util.ArrayList;

public class ListRecentDBsResponse {
    public ArrayList<LoadedDB> recentDBs = new ArrayList<>();
}
