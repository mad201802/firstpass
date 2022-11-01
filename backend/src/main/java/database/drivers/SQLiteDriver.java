package database.drivers;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import database.EncryptedDatabase;
import database.models.CategoryModel;
import database.models.EntryModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDriver {
    static String url = "jdbc:sqlite:database.db";
    Dao<CategoryModel, Integer> categoryDAO;
    Dao<EntryModel, Integer> entryDAO;
    Dao<EncryptedDatabase, Integer> encryptedDAO;
    public  SQLiteDriver() throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url);

        TableUtils.createTableIfNotExists(connectionSource, EntryModel.class);
        entryDAO = DaoManager.createDao(connectionSource, EntryModel.class);

        TableUtils.createTableIfNotExists(connectionSource, EncryptedDatabase.class);
        encryptedDAO = DaoManager.createDao(connectionSource, EncryptedDatabase.class);

        TableUtils.createTableIfNotExists(connectionSource, CategoryModel.class);
        categoryDAO = DaoManager.createDao(connectionSource, CategoryModel.class);
    }


}
