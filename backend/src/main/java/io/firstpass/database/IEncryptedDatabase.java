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
    int createEntry(String name, CipherData username, CipherData password, int categoryID, String url, String notes);
    EncryptedEntryModel getEntryById(int id);
    List<EncryptedEntryModel> getAllEntries();
    List<EncryptedEntryModel> getAllEntriesByCategory(int category_id);
    int updateEntry(int entry_id, String name, CipherData username, CipherData password, int categoryID, String url, String notes);
    boolean deleteEntryById(int id);

    // Categories
    int createCategory(String name);
    CategoryModel getCategory(int id);
    int updateCategory(int id, String name);
    List<CategoryModel> getAllCategories();
    boolean deleteCategory(int id);


    // Meta
    int createMeta(String name, String value);
    MetaModel getMeta(int id);
    MetaModel getMeta(String name);
    HashMap<String, String> getAllMeta();
    boolean updateMeta(int id, String key, String value);
    boolean deleteMeta(int id);

    String getEncryptionAlgorithm();

    boolean close();
}
