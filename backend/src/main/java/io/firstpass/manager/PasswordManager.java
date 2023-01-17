package io.firstpass.manager;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.manager.models.EntryModel;

import java.util.ArrayList;
import java.util.List;

public class PasswordManager {

    private final IEncryptedDatabase database;
    private final ISymmetricEncryptionAlgorithm encryptionAlgorithm;
    private final String masterpassword;

    public PasswordManager(IEncryptedDatabase database, ISymmetricEncryptionAlgorithm encryptionAlgorithm, String masterpassword) {
        this.database = database;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.masterpassword = masterpassword;
    }

    public int createEntry(String name, String username, String password, int category_id, String url, String notes) {
        CipherData encryptedUsername = encryptionAlgorithm.encryptText(username, masterpassword);
        CipherData encryptedPassword = encryptionAlgorithm.encryptText(password, masterpassword);
        return database.createEntry(name, encryptedUsername, encryptedPassword, category_id, url, notes);
    }
    public EntryModel getEntryById(int id) {
       EncryptedEntryModel encryptedEntryModel= database.getEntryById(id);
         String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
         String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
       return new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword, encryptedEntryModel.getCategory().getId(), encryptedEntryModel.getNotes(), encryptedEntryModel.getUrl());
   }
    public ArrayList<EntryModel> getAllEntriesByCategory() {
       ArrayList<EntryModel> entryModels = new ArrayList<>();
       for (EncryptedEntryModel encryptedEntryModel : database.getAllEntries()) {
           String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
           String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
           entryModels.add(new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword, encryptedEntryModel.getCategory().getId(), encryptedEntryModel.getNotes(), encryptedEntryModel.getUrl()));
       }
       return entryModels;
   }
    public ArrayList<EntryModel> getAllEntriesByCategory(int category_id) {
        ArrayList<EntryModel> entryModels = new ArrayList<>();
        for (EncryptedEntryModel encryptedEntryModel : database.getAllEntriesByCategory(category_id)) {
            String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
            String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
            entryModels.add(new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword, encryptedEntryModel.getCategory().getId(), encryptedEntryModel.getNotes(), encryptedEntryModel.getUrl()));
        }
        return entryModels;
    }

    public EntryModel updateEntry(int id, String name, String username, String password, int category_id, String url, String notes) {
        CipherData encryptedUsername , encryptedPassword;

        if(username != null)
            encryptedUsername = encryptionAlgorithm.encryptText(username, masterpassword);
        else
            encryptedUsername = null;

        if(password != null)
            encryptedPassword = encryptionAlgorithm.encryptText(password, masterpassword);
        else
            encryptedPassword = null;

        database.updateEntry(id, name, encryptedUsername, encryptedPassword, category_id, url, notes);
        return getEntryById(id);
    }

    public void deleteEntryById(int id) {
        database.deleteEntryById(id);
    }


    public int createCategory(String name) {
        return database.createCategory(name);
    }
    public List<CategoryModel> getAllCategories() {
        return database.getAllCategories();
    }
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


    public boolean closeDatabase() {
       return database.close();
   }
}




