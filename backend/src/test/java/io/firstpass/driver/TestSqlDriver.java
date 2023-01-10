package io.firstpass.driver;

import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.encryption.symmetric.models.CipherData;
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
        try{
            db = new SQLiteDriver(testDbFile,password);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        File file = new File(testDbFile);
        if(file.exists()){
            file.delete();
        }
    }
    @Test
    public void test_add_entry(){
        CipherData username = new CipherData();
        username.iv = "iv";
        username.text = "username";
        CipherData password = new CipherData();
        password.iv = "iv";
        password.text = "password";

        db.addEntry("Name", username, password, 0, "Category", "Notes");
        EncryptedEntryModel emc = db.getEntry("Name");
        assertEquals("Name", emc.getName());
    }


}
