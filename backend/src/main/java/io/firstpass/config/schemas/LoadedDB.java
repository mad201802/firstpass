package io.firstpass.config.schemas;

/**
 * A database that has been loaded by firstpass.
 */
public class LoadedDB {
    public String name;
    public String filepath;

    public LoadedDB(String name, String filepath) {
        this.name = name;
        this.filepath = filepath;
    }

}
