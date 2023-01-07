package io.firstpass.config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Configuration<T> {
    private T config;
    private final String filepath, filename;
    private final Gson gson;
    private boolean createFolderStructure;

    private File configFile;

    public Configuration(T defaultConfiguration, String filepath, String filename, Boolean createFolderStructure) {
        this.config = defaultConfiguration;
        this.filepath = filepath;
        this.filename = filename;
        this.createFolderStructure = createFolderStructure;
        this.gson = new Gson();
        this.configFile = new File(filepath + "/" + this.filename + ".json");
    }

    /**
     * Saves the current configuration under the specified filepath.
     * The file name is the name of the class that's being saved.
     */
    public void saveConfig(){
        try {
            FileWriter fileWriter = new FileWriter(filepath + "/" + filename + ".json");
            gson.toJson(config, fileWriter);
            fileWriter.close();
            System.out.println("Saved config.");
        } catch (Exception e) {
            System.err.println("Something went wrong while saving the config!");
            System.err.println(e);
        }

    }

    // TODO: Doesn't work because "config" is a LinkedTreeMap and not T

    /**
     * Returns the currently initialized configuration.
     * @return
     * The currently initialized configuration.
     */
    public T getConfig() {
        return config;
    }

    /**
     * If configuration file exists, initialize (read) it, else save it to the file system.
     */
    public void initConfig() {
        if(configExists()) {
            try {
                FileReader fileReader = new FileReader(filepath + "/" + filename + ".json");
                Type type = new TypeToken<T>(){}.getType();
                System.out.println("Type: " + type);
                config = gson.fromJson(fileReader, type);
                fileReader.close();
                System.out.println("Initialized config.");
            } catch (Exception e) {
                System.err.println("Something went wrong while initializing the config!");
                System.err.println(e);
            }

        } else {
            System.out.println("Config didn't exist. Saved current config.");
            saveConfig();
        }
    }

    /**
     * Checks if the configuration directory and file exist.
     * If the directory does not exist, create it based on the setting "createFolderStructure".
     * @return
     * True, if directory and file exist, false if not.
     */
    public boolean configExists() {
        File configFolder = new File(filepath);
        // Check if the directory already exists.
        if(!configFolder.isDirectory()) {
            System.out.println("Config directory doesn't exist!");
            if(createFolderStructure) {
                // Try to create the config directory and/or parent directories.
                boolean createdDir = new File(filepath).mkdirs();
                if (!createdDir) {
                    System.err.println("Couldn't create config directory and/or parent directories!");
                } else {
                    System.out.println("Created config directory and/or parent directories.");
                }
            } else {
                // Try to only create the config directory itself.
                boolean createdDir = new File(filepath).mkdir();
                if (!createdDir) {
                    System.err.println("Couldn't create config directory!");
                } else {
                    System.out.println("Created config directory.");
                }
            }
        } else {
            System.out.println("Config directory already exists.");
        }

        // Check if the config file exists.
        Path path = Paths.get(filepath + "/" + filename + ".json");
        return Files.exists(path);
    }

    /**
     * Deletes the configuration file.
     */
    public void deleteConfigFile() {
        File fileToDelete = configFile;
        if(fileToDelete.delete()) {
            System.out.println("Deleted file " + fileToDelete.getName());
        } else {
            System.err.println("Failed to delete the file " + fileToDelete.getName());
            System.err.println("Folder: " + fileToDelete.getAbsolutePath());
        }
    }

}
