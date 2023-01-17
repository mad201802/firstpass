package io.firstpass.config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.firstpass.utils.Utils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Configuration<T> {
    private T config;
    private final String path;
    private final String filepath;
    private final Gson gson;
    private final boolean createFolderStructure;
    private final File configFile;

    /**
     * A program configuration for firstpass.
     * @param defaultConfiguration
     * The type the configuration should be based on.
     * @param path
     * The path to the directory firstpass should save the config file in.
     * @param filename
     * The filename the config file should receive.
     * @param createFolderStructure
     * True: Firstpass creates missing parent directories of the specified configuration directory.
     * False: Firstpass only attempts to create the configuration directory itself.
     */
    public Configuration(T defaultConfiguration, String path, String filename, Boolean createFolderStructure) {
        this.config = defaultConfiguration;
        this.path = path;
        this.createFolderStructure = createFolderStructure;
        this.gson = new Gson();
        this.filepath = path + File.separator + filename + ".json";
        this.configFile = new File(this.filepath);
    }

    /**
     * A program configuration for firstpass.
     * Automatically sets configuration directory based on detected operating system.
     * @param defaultConfiguration
     * The type the configuration should be based on.
     * @param filename
     * The filename the config file should receive
     * @param createFolderStructure
     * True: Firstpass creates missing parent directories of the specified configuration directory.
     * False: Firstpass only attempts to create the configuration directory itself.
     */
    public Configuration(T defaultConfiguration, String filename, Boolean createFolderStructure) {
        this.config = defaultConfiguration;
        this.path = autoPickPath();
        this.createFolderStructure = createFolderStructure;
        this.gson = new Gson();
        this.filepath = path + File.separator + filename + ".json";
        this.configFile = new File(this.filepath);
    }

    /**
     * Saves the current configuration under the specified filepath.
     * The file name is the name of the class that's being saved.
     */
    public void saveConfig() {
        try {
            FileWriter fileWriter = new FileWriter(this.filepath);
            gson.toJson(config, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            //TODO: Logger
        }

    }

    // TODO: Doesn't work because "config" is a LinkedTreeMap and not T

    /**
     * Returns the currently initialized configuration.
     * @return
     * The currently initialized configuration.
     */
    public T getConfig() {
        return this.config;
    }

    /**
     * If configuration file exists, initialize (read) it, else save it to the file system.
     */
    public void initConfig(){
        if(configExists()) {
            try {
                FileReader fileReader = new FileReader(this.filepath);
                Type type = TypeToken.getParameterized(config.getClass()).getType();
                config = gson.fromJson(fileReader, type);
                fileReader.close();
            } catch (IOException ex) {
                config = null;
            }
        } else {
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
        File configFolder = new File(path);
        // Check if the directory already exists.
        if(!configFolder.isDirectory()) {
            if(createFolderStructure) {
                // Try to create the config directory and/or parent directories.
                boolean createdDir = new File(path).mkdirs();
                if (!createdDir) {
                    //TODO: Log error
                } else {
                    //TODO: Log success
                }
            } else {
                // Try to only create the config directory itself.
                boolean createdDir = new File(path).mkdir();
                if (!createdDir) {
                    //TODO: Log error
                } else {
                    //TODO: Logger
                }
            }
        } else {
            //TODO: Logger
        }

        // Check if the config file exists.
        Path path = Paths.get(this.filepath);
        return Files.exists(path);
    }

    /**
     * Automatically detects the current OS and returns a configuration path.
     * @return
     * The configuration path matching the detected OS.
     */
    private String autoPickPath() {
        String osName = System.getProperty("os.name");
        if(osName.startsWith("Windows")) {
            return System.getenv("APPDATA") + "\\firstpass";
        } else if(osName.toLowerCase().contains("linux")) {
            return "~/.config/firstpass";
        } else if(osName.toLowerCase().contains("mac os")) {
            return "~/.config/firstpass";
        } else {
            Utils.log("OS detection failed! Set path to NULL.");
            return null;
        }

    }

    /**
     * Deletes the configuration file.
     */
    public void deleteConfigFile() {
        if(configFile.delete()) {
            //TODO: Logger
        }
    }

}
