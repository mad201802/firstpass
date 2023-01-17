package io.firstpass.config;


import io.firstpass.utils.Utils;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.Arrays;
import java.util.Properties;

/*
    https://duckduckgo.com/?q=java+check+if+file+with+extention+exists&kak=-1&kao=-1&kap=-1&kaq=-1&kau=-1&kav=1&kax=-1&kl=de-de&kp=-2&atb=v1-1&ia=web&iax=qa
    https://stackoverflow.com/questions/22748484/checking-if-properties-file-exists-and-has-required-properties
    https://www.tutorialspoint.com/Check-if-a-file-exists-in-Java
    https://www.baeldung.com/java-properties
    https://medium.com/@sonaldwivedi/how-to-read-config-properties-file-in-java-6a501dc96b25
    https://stackoverflow.com/questions/32429859/different-ways-of-saving-program-settings-in-java
     */
public class ConfigurationManager {

    private Properties sysProperties;
    private String configDirectory, configFileName;
    public ConfigurationManager(String configDirectory, String configFileName) {
        this.configDirectory = configDirectory;
        this.configFileName = configFileName;
        if(this.initializeConfig()) {
            Utils.log("Config created or available!");
        } else {
            Utils.log("Config not available...");
        }
    }

    public String getProperty(String key) {
        String result = "Nothing there!";
        try{
             result = this.sysProperties.getProperty(key);
        } catch (Exception e) {
            Utils.log(e.getMessage());
            return result;
        }
        if(result.contains("|")) {
            Utils.log("<---------------------------------------------->");
            Utils.log("WARNING! Did you try to get a saved array?");
            Utils.log("Use getPropertyArray() instead :)");
            Utils.log("<---------------------------------------------->");
        }
        return result;
    }
    public String[] getPropertyArray(String key) {
        String[] result = new String[] {};
        String data;
        try{
            data = this.sysProperties.getProperty(key);
        } catch (Exception e) {
            Utils.log(e.getMessage());
            return result;
        }
        result = data.split("\\|");
        return result;
    }

    public void setProperty(String key, String value) {
        try {
            sysProperties.setProperty(key, value);
            sysProperties.store(new FileWriter(this.configDirectory + "/" + this.configFileName), "Updated config via setProperty()...");

        } catch(Exception e) {
            Utils.log("An error occurred while setting property " + key + " to value " + value + " !");
            Utils.log(e.getMessage());
            return;
        }
        Utils.log("Set property "+ key + " to value " + value);
    }
    public void setProperty(String key, String[] values) {
        final String separator = "|";
        StringBuilder dataToStore;
        if(values == null || values.length == 0) {
            dataToStore = new StringBuilder();
        } else {
            dataToStore = new StringBuilder(values[0]);
            for(int i = 1; i < values.length; ++i) {

                dataToStore.append(separator).append(values[i]);
            }
        }
        setProperty(key, dataToStore.toString());
    }

    public boolean propertyExists(String key) {
        return this.sysProperties.containsKey(key);
    }

    public boolean initializeConfig() {
        boolean configDirectory, configFile;
        try{
            configDirectory = this.createConfigDirectory();
            configFile = this.createConfigFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(configDirectory && configFile) {
            sysProperties = new Properties();
            try {
                sysProperties.load(new FileInputStream(this.configDirectory + "/" + this.configFileName));
                boolean hasReqKeys = this.hasRequiredKeys(sysProperties);
                Utils.log("File has required keys: " + hasReqKeys);

                if(!hasReqKeys) {
                    // TODO: Implement automatic creation of missing keys
                    sysProperties.setProperty("test", "wert");
                    sysProperties.store(new FileWriter(this.configDirectory + "/" + this.configFileName), "Had to set missing keys...");
                    Utils.log("Had to set missing keys...");
                }
            } catch (IOException e) {
                Utils.log("Couldn't load properties!");
                Utils.log(e.getMessage());
            }
            return true;
        }
        Utils.log("Something went wrong while initializing the config!");
        return false;


    }

    public boolean createConfigDirectory() throws FileSystemException {
        File folderToCheck = new File(this.configDirectory);
        if (!folderToCheck.isDirectory()) {
            boolean createdDir = new File(this.configDirectory).mkdirs();
            if (!createdDir) {
                Utils.log("Couldn't create config directory!");
                throw new FileSystemException(null);
            }
        }
        return true;
    }

    public boolean createConfigFile() throws IOException {
        String[] fileNameSplitted = this.configFileName.split("\\.(?=[^\\.]+$)");
        boolean alreadyExists = this.fileInDirectory(fileNameSplitted[0], this.configDirectory);
        Utils.log("File already exists: " + alreadyExists);
        if (!alreadyExists) {
            boolean fileCreated = new File(this.configDirectory + "/" + this.configFileName).createNewFile();
            Utils.log("Created new file: " + fileCreated);
            if(!fileCreated) {
                return false;
            }
        }
        return true;
    }

    public boolean fileInDirectory(String filename, String directory) {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        Utils.log("List of files in config dir: " + Arrays.toString(listOfFiles));
        if (listOfFiles == null) {
            Utils.log("No files in config dir!");
            return false;
        }
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] tempFilename = file.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
                if (tempFilename[0].equalsIgnoreCase(filename)) { //matching defined filename
                    Utils.log("File exist: " + tempFilename[0] + "." + tempFilename[1]); // match occures.Apply any condition what you need
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasRequiredKeys(Properties properties) throws IOException {
        Properties tmp = new Properties();
        tmp.setProperty("test", "wert");
        for(String key : tmp.stringPropertyNames()) {
            if(!properties.containsKey(key)){
                return false;
            }
        }
        return true;
    }

}
