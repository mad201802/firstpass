package io.firstpass.config;


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
    private String configDirectory;
    public ConfigurationManager(String configDirectory) {
        this.configDirectory = configDirectory;
        if(this.initializeConfig()) {
            System.out.println("Config created or available!");
        } else {
            System.out.println("Config not available...");
        }
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
                sysProperties.load(new FileInputStream("C:/users/Leo/AppData/Local/firstpass/run.properties"));
                boolean hasReqKeys = this.hasRequiredKeys(sysProperties);
                System.out.println("File has required keys: " + hasReqKeys);

                if(!hasReqKeys) {
                    // TODO: Implement automatic creation of missing keys
                    sysProperties.setProperty("test", "wert");
                    sysProperties.store(new FileWriter("C:/users/Leo/AppData/Local/firstpass/run.properties"), "Had to set missing keys...");
                    System.out.println("Had to set missing keys...");
                }
            } catch (IOException e) {
                System.out.println("Couldn't load properties!");
                System.out.println(e);
            }
            return true;
        }
        System.out.println("Something went wrong while initializing the config!");
        return false;


    }

    public boolean createConfigDirectory() throws FileSystemException {
        File folderToCheck = new File("C:/users/Leo/AppData/Local/firstpass");
        if (!folderToCheck.isDirectory()) {
            boolean createdDir = new File("C:/users/Leo/AppData/Local/firstpass").mkdirs();
            if (!createdDir) {
                System.out.println("Couldn't create config directory!");
                throw new FileSystemException(null);
            }
        }
        return true;
    }

    public boolean createConfigFile() throws IOException {
        boolean alreadyExists = this.fileInDirectory("run", "C:/users/Leo/AppData/Local/firstpass");
        System.out.println("File already exists: " + alreadyExists);
        if (!alreadyExists) {
            boolean fileCreated = new File("C:/users/Leo/AppData/Local/firstpass/run.properties").createNewFile();
            System.out.println("Created new file: " + fileCreated);
            if(!fileCreated) {
                return false;
            }
        }
        return true;
    }

    public boolean fileInDirectory(String filename, String directory) {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        System.out.println("List of files in config dir: " + Arrays.toString(listOfFiles));
        if (listOfFiles == null) {
            System.out.println("No files in config dir!");
            return false;
        }
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String[] tempFilename = file.getName().split("\\.(?=[^\\.]+$)"); //split filename from it's extension
                if (tempFilename[0].equalsIgnoreCase(filename)) { //matching defined filename
                    System.out.println("File exist: " + tempFilename[0] + "." + tempFilename[1]); // match occures.Apply any condition what you need
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
