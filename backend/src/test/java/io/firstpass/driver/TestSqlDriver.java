package io.firstpass.driver;

import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.encryption.symmetric.models.CipherData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.sql.SQLException;


public class TestSqlDriver {
    private static SQLiteDriver db;
    private static final String testDbFile = "./test.db";
    private static final String password = "testpassword";
    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up test database");
        File path = new File(testDbFile);
        boolean bDeleted = path.delete();
        System.out.println("Could delete database: " + bDeleted);
        try{
            db = new SQLiteDriver(testDbFile,password);
            System.out.println("Database created");
        } catch(SQLException e){
            e.printStackTrace();
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

        db.createEntry("Name", username, password, 1, "Category", "Notes");
        EncryptedEntryModel emc = db.getEntryById(1);
        Assertions.assertEquals("Name", emc.getName());
    }


    @Test
    public void test_getAllCategories() {
        int size = db.getAllCategories().size();
        Assertions.assertEquals(1, size); //currently 8 categories  in the database
    }

    @Test
    public void test_getAllEntries() {
        int size = db.getAllEntries().size();
        Assertions.assertEquals(0, size); //currently 0 entries in the database
    }

    @AfterAll()
    public static void tearDownAll() {
        db.close();
        File file = new File(testDbFile);
        if(file.exists()){
            boolean bDeleted = file.delete();
            System.out.println("Could delete database: " + bDeleted);
        }
    }


}
