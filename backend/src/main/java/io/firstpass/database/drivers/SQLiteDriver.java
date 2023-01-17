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
    private static final String DATABASE_VERSION = "0.2.0";
    private final ConnectionSource connectionSource;
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

    /**
     * This method is used to add a new entry to the io.firstpass.database.
     * @param name The name of the entry.
     * @param username The username of the entry.
     * @param password The password of the entry.
     * @return The ID of the entry.
     */
    @Override
    public int createEntry(String name, CipherData username, CipherData password, int categoryID, String url, String notes) {
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
     * Gets a entry from the io.firstpass.database.
     * @param id The ID of the entry.
     * @return The entry.
     */
    @Override
    public EncryptedEntryModel getEntryById(int id) {
        try {
            return entryDAO.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }
    @Override
    public List<EncryptedEntryModel> getAllEntries() {
        try {
            return entryDAO.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }
    @Override
    public List<EncryptedEntryModel> getAllEntriesByCategory(int category_id) {
        try {
            return entryDAO.queryForEq("category_id", category_id);
        } catch (SQLException e) {
            return null;
        }
    }
    @Override
    public int updateEntry(int entry_id, String name, CipherData username, CipherData password, int categoryID, String url, String notes) {
        try {
            EncryptedEntryModel entry = entryDAO.queryForId(entry_id);
            if (entry == null) {
                return -1;
            }

            if(categoryID != -1) {
                CategoryModel category = categoryDAO.queryForId(categoryID);
                //if category is null, set it to Uncategorized
                if (category == null) {
                    return -1;
                }

                entry.setCategory(category);
            }

            if(name != null) {
                entry.setName(name);
            }

            if(username != null) {
                System.out.println("Updating username");
                System.out.println(username.text);
                EncryptedModel usernameModel = encryptedDAO.queryForEq("id", entry.getUsername().getId()).get(0);
                usernameModel.setCipherData(username);
                encryptedDAO.update(usernameModel);
            }

            if(password != null) {
                System.out.println("Updating password");
                EncryptedModel passwordModel = encryptedDAO.queryForEq("id", entry.getPassword().getId()).get(0);
                passwordModel.setCipherData(password);
                encryptedDAO.update(passwordModel);
            }

            if(url != null) {
                entry.setUrl(url);
            }

            if(notes != null) {
                entry.setNotes(notes);
            }

            if (entryDAO.update(entry) == 1) {
                return entry.getId();
            }
        } catch (SQLException e) {
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
    public boolean deleteEntryById(int id) {
        try {
            return entryDAO.deleteById(id) == 1;
        } catch (SQLException e) {
            return false;
        }
    }


    @Override
    public int createCategory(String name) {
        try {
            CategoryModel categoryModel = new CategoryModel(name);
            categoryDAO.create(categoryModel);
            return categoryModel.getId();
        } catch (SQLException e) {
            return -1;
        }
    }
    @Override
    public CategoryModel getCategory(int id) {
        try {
            return categoryDAO.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }
    @Override
    public List<CategoryModel> getAllCategories() {
        try {
            return categoryDAO.queryForAll();
        } catch (SQLException e) {
            return null;
        }
    }
    @Override
    public int updateCategory(int id, String name) {
        return 0;
    }
    @Override
    public boolean deleteCategory(int id) {
        try {
            return categoryDAO.deleteById(id) == 1;
        } catch (SQLException e) {
            return false;
        }
    }


    @Override
    public int createMeta(String name, String value) {
        try {
            MetaModel metaModel = new MetaModel(name, value);
            metaDAO.create(metaModel);
            return metaModel.getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    @Override
    public MetaModel getMeta(int id) {
        try {
            return metaDAO.queryForId(id);
        } catch (SQLException e) {
            return null;
        }
    }
    @Override
    public MetaModel getMeta(String name) {
        try {
            return metaDAO.queryForEq("name", name).get(0);
        } catch (SQLException e) {
            return null;
        }
    }
    @Override
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
    @Override
    public boolean deleteMeta(int id) {
        try {
            return metaDAO.deleteById(id) == 1;
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
        } catch (SQLException e) {
            e.printStackTrace();
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
