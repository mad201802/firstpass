package io.firstpass.driver;

import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.database.models.CategoryModel;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import org.junit.*;


import static org.junit.Assert.*;

public class TestSqlDriver {
    private SQLiteDriver db;
    private final String testDbFile = "test.db";
    private final String password = "testpassword";
    @Before
    public void setUp() throws Exception {
        db = new SQLiteDriver(testDbFile,password);
    }

    @After
    public void tearDown() throws Exception {
        File file = new File(testDbFile);
        if(file.exists()){
            file.delete();
        }
    }

}
