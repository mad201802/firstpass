package database.drivers;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import database.IEncryptedDatabase;
import database.models.EncryptedModel;
import database.models.CategoryModel;
import database.models.EntryModel;
import encryption.symmetric.models.CipherData;

import java.sql.SQLException;

/**
 * This class is used to connect to a SQLite database.
 */
public class SQLiteDriver implements IEncryptedDatabase {
    Dao<CategoryModel, Integer> categoryDAO;
    Dao<EntryModel, Integer> entryDAO;
    Dao<EncryptedModel, Integer> encryptedDAO;

    /**
     * This method is used to connect to a SQLite database.
     * @param filepath The path to the database file.
     * @throws SQLException If the connection fails.
     */
    public  SQLiteDriver(String filepath) throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + filepath);

        encryptedDAO = DaoManager.createDao(connectionSource, EncryptedModel.class);
        TableUtils.createTableIfNotExists(connectionSource, EncryptedModel.class);

        categoryDAO = DaoManager.createDao(connectionSource, CategoryModel.class);
        TableUtils.createTableIfNotExists(connectionSource, CategoryModel.class);

        entryDAO = DaoManager.createDao(connectionSource, EntryModel.class);
        TableUtils.createTableIfNotExists(connectionSource, EntryModel.class);
    }

    /**
     * This method is used to add a new entry to the database.
     * @param name The name of the entry.
     * @param username The username of the entry.
     * @param password The password of the entry.
     * @return The ID of the entry.
     */
    @Override
    public int addEntry(String name, CipherData username, CipherData password) {
        try {
            EncryptedModel usernameModel = new EncryptedModel(username.text, username.iv);
            encryptedDAO.create(usernameModel);

            EncryptedModel passwordModel = new EncryptedModel(password.text, password.iv);
            encryptedDAO.create(passwordModel);

            EntryModel entryModel = new EntryModel(name);
            entryModel.setUsername(usernameModel);
            entryModel.setPassword(passwordModel);
            if (entryDAO.create(entryModel) == 1) {
                return entryModel.getId();
            }
        } catch (SQLException e) {
            return -1;
        }
        return -1;
    }

    /**
     * Deletes a entry from the database.
     * @param id The ID of the entry.
     * @return True if the entry was deleted, false otherwise.
     */
    @Override
    public boolean deleteEntry(int id) {
        try {
            return entryDAO.deleteById(id) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Gets a entry from the database.
     * @param id The ID of the entry.
     * @return The entry.
     */
    @Override
    public EntryModel getEntry(int id) {
        try {
            return entryDAO.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Gets a entrie based on the name.
     * @param name The name of the entry.
     * @return The entry.
     */
    @Override
    public EntryModel getEntry(String name) {
        try {
            return entryDAO.queryForEq("name", name).get(0);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Gets all entries from the database.
     * @return All entries.
     */
    @Override
    public EntryModel[] getEntries() {
        try {
            return entryDAO.queryForAll().toArray(new EntryModel[0]);
        } catch (SQLException e) {
            return null;
        }
    }
}
