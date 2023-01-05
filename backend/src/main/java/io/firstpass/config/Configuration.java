package io.firstpass.config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Configuration<T> {
    private T config;
    private final String filepath;
    private final Gson gson;
    private boolean createFolderStructure;

    public Configuration(T defaultConfiguration, String filepath, Boolean createFolderStructure) {
        this.config = defaultConfiguration;
        this.filepath = filepath;
        this.createFolderStructure = createFolderStructure;
        this.gson = new Gson();

    }

    public void saveConfig(){
        try {
            FileWriter fileWriter = new FileWriter(filepath + "/" + this.config.getClass().getSimpleName() + ".json");
            gson.toJson(config, fileWriter);
            fileWriter.close();
            System.out.println("Saved config.");
        } catch (Exception e) {
            System.err.println("Something went wrong while saving the config!");
            System.err.println(e);
        }

    }

    // TODO: Doesn't work because "config" is a LinkedTreeMap and not T
    public T getConfig() {
        return config;
    }

    public void initConfig() {
        if(configExists()) {
            try {
                FileReader fileReader = new FileReader(filepath + "/" + this.config.getClass().getSimpleName() + ".json");
                Type type = new TypeToken<T>(){}.getType();
                config = gson.fromJson(fileReader, type);
                fileReader.close();
                System.out.println("Initialized config.");
            } catch (Exception e) {
                System.err.println("Something went wrong while initializing the config!");
                System.err.println(e);
            }

        } else {
            saveConfig();
        }
    }

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
        Path path = Paths.get(filepath + "/" + this.config.getClass().getSimpleName() + ".json");
        return Files.exists(path);
    }

}
