package database;

import database.models.EntryModel;
import encryption.symmetric.models.CipherData;

/**
 * Interface for an encrypted database.
 */
public interface IEncryptedDatabase {
    int addEntry(String name, CipherData username, CipherData password);
    // void updateEntry(int id, String name, CipherData username, CipherData password);
    boolean deleteEntry(int id);
    EntryModel getEntry(int id);
    EntryModel getEntry(String name);
    EntryModel[] getEntries();
}
