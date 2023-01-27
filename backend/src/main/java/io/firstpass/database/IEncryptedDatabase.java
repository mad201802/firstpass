package io.firstpass.database;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.database.models.MetaModel;
import io.firstpass.encryption.symmetric.models.CipherData;

import java.util.HashMap;
import java.util.List;

/**
 * Interface for an encrypted io.firstpass.database
 */
public interface IEncryptedDatabase {

    // Entries

    /**
     * Creates a new entry in the io.firstpass.database.
     * @param name The name of the entry.
     * @param username The username of the entry.
     * @param password The password of the entry.
     * @param categoryID The category ID of the entry.
     * @param url The URL of the entry.
     * @param notes The notes of the entry.
     * @return The ID of the newly created entry.
     */
    int createEntry(String name, CipherData username, CipherData password, int categoryID, String url, String notes);

    /**
     * Gets an entry by its ID.
     * @param id The ID of the entry.
     * @return The entry.
     */
    EncryptedEntryModel getEntryById(int id);

    /**
     * Gets all entries from the database
     * @return A list of all entries.
     */
    List<EncryptedEntryModel> getAllEntries();

    /**
     * Gets all entries from a category.
     * @param category_id The ID of the category.
     * @return A list of all entries in the category.
     */
    List<EncryptedEntryModel> getAllEntriesByCategory(int category_id);

    /**
     * Updates an entry in the io.firstpass.database.
     * @param entry_id The ID of the entry.
     * @param name The name of the entry.
     * @param username The username of the entry.
     * @param password The password of the entry.
     * @param categoryID The category ID of the entry.
     * @param url The URL of the entry.
     * @param notes The notes of the entry.
     * @return The ID of the updated entry.
     */
    int updateEntry(int entry_id, String name, CipherData username, CipherData password, int categoryID, String url, String notes);

    /**
     * Deletes an entry from the database
     * @param id The ID of the entry.
     * @return True if the entry was deleted, false if not.
     */
    boolean deleteEntryById(int id);

    // Categories

    /**
     * Creates a new category in the database.
     * @param name The name of the category.
     * @return The ID of the newly created category.
     */
    int createCategory(String name);

    /**
     * Gets a category by its ID.
     * @param id The ID of the category.
     * @return The category.
     */
    CategoryModel getCategory(int id);

    /**
     * Updates a category in the database.
     * @param id The ID of the category.
     * @param name The name of the category.
     * @return The ID of the updated category.
     */
    int updateCategory(int id, String name);

    /**
     * Gets all categories from the database.
     * @return A list of all categories.
     */
    List<CategoryModel> getAllCategories();

    /**
     * Deletes a category from the database.
     * @param id The ID of the category.
     * @return True if the category was deleted, false if not.
     */
    boolean deleteCategory(int id);


    // Meta

    /**
     * Creates a new meta entry in the database.
     * @param key The key of the meta entry.
     * @param value The value of the meta entry.
     * @return The ID of the newly created meta entry.
     */
    int createMeta(String key, String value);

    /**
     * Gets a meta entry by its ID.
     * @param id The ID of the meta entry.
     * @return The meta entry.
     */
    MetaModel getMeta(int id);

    /**
     * Gets a meta entry by its key.
     * @param key The key of the meta entry.
     * @return The meta entry.
     */
    MetaModel getMeta(String key);

    /**
     * Gets all meta entries from the database.
     * @return A list of all meta entries.
     */
    HashMap<String, String> getAllMeta();

    /**
     * Updates a meta entry in the database.
     * @param key The key of the meta entry.
     * @param value The value of the meta entry.
     * @return True if the meta entry was updated, false if not.
     */
    boolean updateMeta(String key, String value);

    /**
     * Deletes a meta entry from the database.
     * @param id The ID of the meta entry.
     * @return True if the meta entry was deleted, false if not.
     */
    boolean deleteMeta(int id);


    /**
     * Gets the encryption algorithm used by the database.
     * @return The encryption algorithm.
     */
    String getEncryptionAlgorithm();

    /**
     * Close the database connection.
     * @return True if the connection was closed, false if not.
     */
    boolean close();
}
