package io.firstpass.config;

import io.firstpass.config.schemas.DefaultConfig;
import io.firstpass.config.schemas.LoadedDB;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.File;
import java.util.ArrayList;

public class TestConfiguration {

    @Test
    public void test_loadedDBs() {
        String currentDirectory;
        currentDirectory = new File("").getAbsolutePath();
        System.out.println("Current working directory : "+currentDirectory);


        DefaultConfig defaultConfig = new DefaultConfig();
        LoadedDB db = new LoadedDB("test_db", "test_filepath");
        defaultConfig.recentDBs = new ArrayList<LoadedDB>();
        defaultConfig.recentDBs.add(db);
        Configuration<DefaultConfig> configuration = new Configuration<DefaultConfig>(defaultConfig, currentDirectory, "testconfig", false);
        configuration.initConfig();
        System.out.println("Currently initialized config 1: " +  configuration.getConfig());

        Configuration<DefaultConfig> configuration2 = new Configuration<DefaultConfig>(defaultConfig, currentDirectory, "testconfig", false);
        configuration2.initConfig();
        System.out.println("Currently initialized config 2: " +  configuration2.getConfig());

        Assertions.assertEquals(configuration.getConfig().recentDBs.size(), configuration2.getConfig().recentDBs.size());
        Assertions.assertEquals(configuration.getConfig().recentDBs.get(0).name, configuration2.getConfig().recentDBs.get(0).name);
        Assertions.assertEquals(configuration.getConfig().recentDBs.get(0).filepath, configuration2.getConfig().recentDBs.get(0).filepath);

        configuration.deleteConfigFile();
    }

//    @Test
//    public void test_createFolderStructure() {
//        String currentDirectory;
//        currentDirectory = new File("").getAbsolutePath();
//        System.out.println("Current working directory : "+currentDirectory);
//        DefaultConfig defaultConfig = new DefaultConfig();
//        Configuration<DefaultConfig> configuration = new Configuration<DefaultConfig>(defaultConfig, currentDirectory + File.separator + "fs_test" + File.separator + "didNotExistBefore", "testconfig", true);
//        Assertions.assertDoesNotThrow(configuration.initConfig());
//    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    // TODO: @SystemProperty(name = "os.name", value = "Windows") -> Wäre mit den Junit Extensions möglich :)
    public void test_autoPickPathWin() {
        DefaultConfig defaultConfig = new DefaultConfig();
        Configuration<DefaultConfig> configuration = new Configuration<DefaultConfig>(defaultConfig, "testconfig", false);
        Assertions.assertEquals(System.getenv("APPDATA") + "\\firstpass\\testconfig.json", configuration.getFilepath());
    }

    @Test
    @EnabledOnOs({OS.LINUX})
    public void test_autoPickPathLinux() {
        DefaultConfig defaultConfig = new DefaultConfig();
        Configuration<DefaultConfig> configuration = new Configuration<DefaultConfig>(defaultConfig, "testconfig", false);
        Assertions.assertEquals("~/.config/firstpass/testconfig.json", configuration.getFilepath());
    }
}
