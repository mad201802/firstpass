package io.firstpass.database;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.database.models.MetaModel;
import io.firstpass.encryption.symmetric.models.CipherData;

import java.util.List;

/**
 * Interface for an encrypted io.firstpass.database.
 */
public interface IEncryptedDatabase {
    // Entries
    int addEntry(String name, CipherData username, CipherData password, int categoryID, String url, String notes);
    EncryptedEntryModel getEntry(int id);
    EncryptedEntryModel getEntry(String name);
    EncryptedEntryModel[] getEntries();
    boolean deleteEntry(int id);

    // Categories
    CategoryModel getCategory(int id);
    CategoryModel getCategory(String name);
    List<CategoryModel> getAllCategories();


    // Meta
    int addMeta(String name, String value);
    boolean deleteMeta(int id);
    MetaModel getMeta(int id);
    MetaModel getMeta(String name);
    boolean updateMeta(int id, String key, String value);
    String getEncryptionAlgorithm();

    boolean close();
}
