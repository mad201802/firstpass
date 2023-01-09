package io.firstpass.database.drivers;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.database.models.EncryptedModel;
import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.database.models.MetaModel;
import io.firstpass.encryption.hashing.SHA256;
import io.firstpass.encryption.symmetric.models.CipherData;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * This class is used to connect to a SQLite io.firstpass.database.
 */
public class SQLiteDriver implements IEncryptedDatabase {
    private static final String DATABASE_VERSION = "0.1.0";
    private ConnectionSource connectionSource;
    Dao<CategoryModel, Integer> categoryDAO;
    Dao<EncryptedEntryModel, Integer> entryDAO;
    Dao<EncryptedModel, Integer> encryptedDAO;
    Dao<MetaModel, Integer> metaDAO;

    /**
     * This method is used to connect to a SQLite io.firstpass.database.
     * @param filepath The path to the io.firstpass.database file.
     * @throws SQLException If the connection fails.
     */
    public  SQLiteDriver(String filepath, String masterpassword) throws SQLException {
        Logger.setGlobalLogLevel(Level.WARNING);
        this.connectionSource = new JdbcConnectionSource("jdbc:sqlite:" + filepath);

        encryptedDAO = DaoManager.createDao(connectionSource, EncryptedModel.class);
        TableUtils.createTableIfNotExists(connectionSource, EncryptedModel.class);

        categoryDAO = DaoManager.createDao(connectionSource, CategoryModel.class);
        TableUtils.createTableIfNotExists(connectionSource, CategoryModel.class);

        entryDAO = DaoManager.createDao(connectionSource, EncryptedEntryModel.class);
        TableUtils.createTableIfNotExists(connectionSource, EncryptedEntryModel.class);

        metaDAO = DaoManager.createDao(connectionSource, MetaModel.class);
        TableUtils.createTableIfNotExists(connectionSource, MetaModel.class);

        init_meta(masterpassword);

        init_categories();
    }

    private void init_meta(String masterpassword) throws SQLException {
        if (metaDAO.queryForEq("key", "masterpassword").isEmpty()) {
            metaDAO.create(new MetaModel("masterpassword", SHA256.hash(masterpassword)));
        } else {
            if (!metaDAO.queryForEq("key", "masterpassword").get(0).getValue().equals(SHA256.hash(masterpassword))) {
                throw new SQLException("Incorrect master password");
            }
        }

        if (metaDAO.queryForEq("key", "version").isEmpty()) {
            metaDAO.create(new MetaModel("version", DATABASE_VERSION));
        } else {
            if (!metaDAO.queryForEq("key", "version").get(0).getValue().equals(DATABASE_VERSION)) {
                throw new SQLException("Incorrect database version");
            }
        }

        if (metaDAO.queryForEq("key", "encryption_algorithm").isEmpty()) {
            metaDAO.create(new MetaModel("encryption_algorithm", "aes256"));
        }

    }

    private void init_categories() {
        try {
            categoryDAO.createIfNotExists(new CategoryModel(1, "Uncategorized"));
            categoryDAO.createIfNotExists(new CategoryModel(2, "Social Media"));
            categoryDAO.createIfNotExists(new CategoryModel(3, "Banking"));
            categoryDAO.createIfNotExists(new CategoryModel(4, "Shopping"));
            categoryDAO.createIfNotExists(new CategoryModel(5, "Gaming"));
            categoryDAO.createIfNotExists(new CategoryModel(6, "Email"));
            categoryDAO.createIfNotExists(new CategoryModel(7, "Streaming"));
            categoryDAO.createIfNotExists(new CategoryModel(8, "Other"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to add a new entry to the io.firstpass.database.
     * @param name The name of the entry.
     * @param username The username of the entry.
     * @param password The password of the entry.
     * @return The ID of the entry.
     */
    @Override
    public int addEntry(String name, CipherData username, CipherData password, int categoryID, String url, String notes) {
        try {
            CategoryModel category = categoryDAO.queryForId(categoryID);


            //if category is null, set it to Uncategorized
            if(category == null) {
                category = categoryDAO.queryForId(1);
            }




            EncryptedModel usernameModel = new EncryptedModel(username.text, username.iv);
            encryptedDAO.create(usernameModel);

            EncryptedModel passwordModel = new EncryptedModel(password.text, password.iv);
            encryptedDAO.create(passwordModel);

            EncryptedEntryModel encryptedEntryModel = new EncryptedEntryModel(name);
            encryptedEntryModel.setUsername(usernameModel);
            encryptedEntryModel.setPassword(passwordModel);
            encryptedEntryModel.setCategory(category);
            encryptedEntryModel.setUrl(url);
            encryptedEntryModel.setNotes(notes);
            encryptedEntryModel.setCreatedAt(new Date());

            if (entryDAO.create(encryptedEntryModel) == 1) {
                return encryptedEntryModel.getId();
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }

    /**
     * Deletes a entry from the io.firstpass.database.
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
     * Gets a entry from the io.firstpass.database.
     * @param id The ID of the entry.
     * @return The entry.
     */
    @Override
    public EncryptedEntryModel getEntry(int id) {
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
    public EncryptedEntryModel getEntry(String name) {
        try {
            return entryDAO.queryForEq("name", name).get(0);
        } catch (SQLException e) {
            return null;
        }
    }

    //get category
    public CategoryModel getCategory(int id) {
        try {
            return categoryDAO.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    //get category
    public CategoryModel getCategory(String name) {
        try {
            return categoryDAO.queryForEq("name", name).get(0);
        } catch (SQLException e) {
            return null;
        }
    }

    //get all categories
    public List<CategoryModel> getAllCategories() {
        try {
            return categoryDAO.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }

    //get all entries
    public List<EncryptedEntryModel> getAllEntries() {
        try {
            return entryDAO.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }

    //get all entries in a category
    public List<EncryptedEntryModel> getAllEntries(int categoryID) {
        try {
            return entryDAO.queryForEq("category_id", categoryID);
        } catch (SQLException e) {
            return null;
        }
    }

    //count all entries in a category
    public int countAllEntries(int categoryID) {
        try {
            return (int) entryDAO.countOf(entryDAO.queryBuilder().where().eq("category_id", categoryID).prepare());
        } catch (SQLException e) {
            return 0;
        }
    }

    //count all entries
    public int countAllEntries() {
        try {
            return (int) entryDAO.countOf();
        } catch (SQLException e) {
            return 0;
        }
    }


    /**
     * Gets all entries from the io.firstpass.database.
     * @return All entries.
     */
    @Override
    public EncryptedEntryModel[] getEntries() {
        try {
            return entryDAO.queryForAll().toArray(new EncryptedEntryModel[0]);
        } catch (SQLException e) {
            return null;
        }
    }

    //New functions for MetaModel
    public int addMeta(String name, String value) {
        try {
            MetaModel metaModel = new MetaModel(name, value);
            metaDAO.create(metaModel);
            return metaModel.getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public boolean deleteMeta(int id) {
        try {
            return metaDAO.deleteById(id) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public MetaModel getMeta(int id) {
        try {
            return metaDAO.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public MetaModel getMeta(String name) {
        try {
            return metaDAO.queryForEq("name", name).get(0);
        } catch (SQLException e) {
            return null;
        }
    }

    public List<MetaModel> getAllMeta() {
        try {
            return metaDAO.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean updateMeta(int id, String key, String value) {
        try {
            MetaModel metaModel = metaDAO.queryForId(id);
            metaModel.setKey(key);
            metaModel.setValue(value);
            return metaDAO.update(metaModel) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getEncryptionAlgorithm() {
        try {
            return metaDAO.queryForEq("key", "encryption_algorithm").get(0).getValue();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean close() {
        try {
            this.connectionSource.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
