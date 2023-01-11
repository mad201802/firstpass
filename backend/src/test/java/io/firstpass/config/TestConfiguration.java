package io.firstpass.config;

import io.firstpass.config.schemas.DefaultConfig;
import io.firstpass.config.schemas.LoadedDB;
import io.firstpass.ipc.exceptions.IPCException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        defaultConfig.loadedDBs = new ArrayList<LoadedDB>();
        defaultConfig.loadedDBs.add(db);
        Configuration<DefaultConfig> configuration = new Configuration<DefaultConfig>(defaultConfig, currentDirectory, "testconfig", false);
        configuration.initConfig();
        System.out.println("Currently initialized config 1: " +  configuration.getConfig());

        Configuration<DefaultConfig> configuration2 = new Configuration<DefaultConfig>(defaultConfig, currentDirectory, "testconfig", false);
        configuration2.initConfig();
        System.out.println("Currently initialized config 2: " +  configuration2.getConfig());

        Assertions.assertEquals(configuration.getConfig().loadedDBs.size(), configuration2.getConfig().loadedDBs.size());
        Assertions.assertEquals(configuration.getConfig().loadedDBs.get(0).name, configuration2.getConfig().loadedDBs.get(0).name);
        Assertions.assertEquals(configuration.getConfig().loadedDBs.get(0).filepath, configuration2.getConfig().loadedDBs.get(0).filepath);

        configuration.deleteConfigFile();
    }
}
