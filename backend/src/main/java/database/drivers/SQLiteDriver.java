package database.drivers;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import database.models.EncryptedModel;
import database.models.CategoryModel;
import database.models.EntryModel;

import java.sql.SQLException;

public class SQLiteDriver {
    //static String url = "jdbc:sqlite:database.db";
    Dao<CategoryModel, Integer> categoryDAO;
    Dao<EntryModel, Integer> entryDAO;
    Dao<EncryptedModel, Integer> encryptedDAO;
    public  SQLiteDriver(String url) throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(url);

        TableUtils.createTableIfNotExists(connectionSource, EntryModel.class);
        entryDAO = DaoManager.createDao(connectionSource, EntryModel.class);

        TableUtils.createTableIfNotExists(connectionSource, EncryptedModel.class);
        encryptedDAO = DaoManager.createDao(connectionSource, EncryptedModel.class);

        TableUtils.createTableIfNotExists(connectionSource, CategoryModel.class);
        categoryDAO = DaoManager.createDao(connectionSource, CategoryModel.class);
    }

    //add normal entry
    public void addEntry(String name, String password) throws SQLException {
        EntryModel entryModel = new EntryModel(name, password);
        entryDAO.create(entryModel);
    }
    //delete entry
    public void deleteEntry(int id) throws SQLException {
        if (entryDAO.idExists(id)) {
            entryDAO.deleteById(id);
        }
    }
    //update entry
    public void updateEntry(int id, String name, String password) throws SQLException {
        if (entryDAO.idExists(id)) {
            EntryModel entryModel = entryDAO.queryForId(id);
            entryModel.setUsername(name);
            entryModel.setPassword(password);
            entryDAO.update(entryModel);
        }
    }

    //get entry by id
    public EntryModel getEntry(int id) throws SQLException {
        if (entryDAO.idExists(id)) {
            return entryDAO.queryForId(id);
        }
        return null;
    }
    //get entry by name
    public EntryModel getEntry(String name) throws SQLException {
        if (entryDAO.queryForEq("username", name).size() > 0) {
            return entryDAO.queryForEq("username", name).get(0);
        }
        return null;
    }

    //delete all entries by name
    public void deleteAllEntries(String name) throws SQLException {
        entryDAO.delete(entryDAO.queryForEq("username", name));
    }
    //get all entries by in an array
    public EntryModel[] getAllEntries() throws SQLException {
        return entryDAO.queryForAll().toArray(new EntryModel[0]);
    }
}
