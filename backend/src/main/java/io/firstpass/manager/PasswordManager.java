package io.firstpass.manager;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.manager.models.EntryModel;

import java.util.ArrayList;

/**
 * This class is used to manage the passwords
 */
public class PasswordManager {

    private final IEncryptedDatabase database;
    private final ISymmetricEncryptionAlgorithm encryptionAlgorithm;
    private final String masterpassword;

    public PasswordManager(IEncryptedDatabase database, ISymmetricEncryptionAlgorithm encryptionAlgorithm, String masterpassword) {
        this.database = database;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.masterpassword = masterpassword;
    }

    /**
     * Creates a new entry
     * @param name The name of the entry
     * @param username The username of the entry
     * @param password The password of the entry
     * @param category_id The category id of the entry
     * @param url The url of the entry
     * @param notes The notes of the entry
     * @return The id of the entry
     */
    public int createEntry(String name, String username, String password, int category_id, String url, String notes) {
        CipherData encryptedUsername = encryptionAlgorithm.encryptText(username, masterpassword);
        CipherData encryptedPassword = encryptionAlgorithm.encryptText(password, masterpassword);
        return database.createEntry(name, encryptedUsername, encryptedPassword, category_id, url, notes);
    }

    /**
     * Get an entry by id
     * @param id The id of the entry
     * @return The entry
     */
    public EntryModel getEntryById(int id) {
        EncryptedEntryModel encryptedEntryModel = database.getEntryById(id);
        String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
        String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
        return new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword, encryptedEntryModel.getCategory().getId(), encryptedEntryModel.getNotes(), encryptedEntryModel.getUrl());
    }

    /**
     * Get all entries
     * @return A ArrayList of entries
     */
    public ArrayList<EntryModel> getAllEntries() {
        ArrayList<EntryModel> entryModels = new ArrayList<>();
        for (EncryptedEntryModel encryptedEntryModel : database.getAllEntries()) {
            String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
            String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
            entryModels.add(new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword, encryptedEntryModel.getCategory().getId(), encryptedEntryModel.getNotes(), encryptedEntryModel.getUrl()));
        }
        return entryModels;
    }

    /**
     * Get all entries by category
     * @param category_id The category id
     * @return A ArrayList of entries
     */
    public ArrayList<EntryModel> getAllEntriesByCategory(int category_id) {
        ArrayList<EntryModel> entryModels = new ArrayList<>();
        for (EncryptedEntryModel encryptedEntryModel : database.getAllEntriesByCategory(category_id)) {
            String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
            String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
            entryModels.add(new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword, encryptedEntryModel.getCategory().getId(), encryptedEntryModel.getNotes(), encryptedEntryModel.getUrl()));
        }
        return entryModels;
    }

    /**
     * Update an entry
     * @param id The id of the entry
     * @param name The name of the entry
     * @param username The username of the entry
     * @param password The password of the entry
     * @param category_id The category id of the entry
     * @param url The url of the entry
     * @param notes The notes of the entry
     * @return The updated entry
     */
    public EntryModel updateEntry(int id, String name, String username, String password, int category_id, String url, String notes) {
        CipherData encryptedUsername, encryptedPassword;

        if (username != null)
            encryptedUsername = encryptionAlgorithm.encryptText(username, masterpassword);
        else
            encryptedUsername = null;

        if (password != null)
            encryptedPassword = encryptionAlgorithm.encryptText(password, masterpassword);
        else
            encryptedPassword = null;

        database.updateEntry(id, name, encryptedUsername, encryptedPassword, category_id, url, notes);
        return getEntryById(id);
    }

    /**
     * Delete an entry by id
     * @param id The id of the entry
     * @return True if the entry was deleted
     */
    public boolean deleteEntryById(int id) {
        return database.deleteEntryById(id);
    }

    /**
     * Create a new category
     * @param name The name of the category
     * @return The id of the category
     */
    public int createCategory(String name) {
        return database.createCategory(name);
    }


    /**
     * Get all categories
     * @return A ArrayList of categories
     */
    public ArrayList<CategoryModel> getAllCategories() {
        return (ArrayList<CategoryModel>) database.getAllCategories();
    }

    /**
     * Update a category
     * @param id The id of the category
     * @param newName The new name of the category
     * @return True if the category was updated
     */
    public int updateCategory(int id, String newName) {
        return database.updateCategory(id, newName);
    }

    /**
     * Delete a category by id
     * @param id The id of the category
     * @param deleteEntries If true, all entries in the category will be deleted
     * @return True if the category was deleted
     */
    public boolean deleteCategoryById(int id, boolean deleteEntries) {
        ArrayList<EntryModel> entries = getAllEntriesByCategory(id);
        if (deleteEntries) {
            for (EntryModel entry : entries) {
                deleteEntryById(entry.getId());
            }
        } else {
            for (EntryModel entry : entries) {
                database.updateEntry(entry.getId(), entry.getName(), encryptionAlgorithm.encryptText(entry.getUsername(), masterpassword), encryptionAlgorithm.encryptText(entry.getPassword(), masterpassword), 1, entry.getUrl(), entry.getNotes());
            }
        }

        return database.deleteCategory(id);
    }

    /**
     * Closes the database
     * @return True if the database was closed
     */
    public boolean closeDatabase() {
        return database.close();
    }
}




