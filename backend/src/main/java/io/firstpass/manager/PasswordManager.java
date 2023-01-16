package io.firstpass.manager;

import io.firstpass.database.models.CategoryModel;
import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.manager.models.EntryModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PasswordManager {

    IEncryptedDatabase database;
    ISymmetricEncryptionAlgorithm encryptionAlgorithm;
    String masterpassword;

    public PasswordManager(IEncryptedDatabase database, ISymmetricEncryptionAlgorithm encryptionAlgorithm, String masterpassword) {
        this.database = database;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.masterpassword = masterpassword;
    }

    public int addEntry(String name,String username, String password, int category_id, String url, String notes) {
        CipherData encryptedUsername = encryptionAlgorithm.encryptText(username, masterpassword);
        CipherData encryptedPassword = encryptionAlgorithm.encryptText(password, masterpassword);
        return database.addEntry(name, encryptedUsername, encryptedPassword, category_id, url, notes);
    }

   public void removeEntryByID(int id) {
       database.deleteEntry(id);
   }

   public EntryModel getEntryByID(int id) {
       EncryptedEntryModel encryptedEntryModel= database.getEntry(id);
         String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
         String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
       return new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword);
   }
   public ArrayList<EntryModel> getAllEntries() {
       ArrayList<EntryModel> entryModels = new ArrayList<>();
       for (EncryptedEntryModel encryptedEntryModel : database.getEntries()) {
           String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
           String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
           entryModels.add(new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword));
       }
       return entryModels;
   }

    public ArrayList<EntryModel> getAllEntries(int category_id) {
        ArrayList<EntryModel> entryModels = new ArrayList<>();
        for (EncryptedEntryModel encryptedEntryModel : database.getAllEntriesByCategory(category_id)) {
            String decryptedUsername = encryptionAlgorithm.decryptText(encryptedEntryModel.getUsername().getCipherData(), masterpassword);
            String decryptedPassword = encryptionAlgorithm.decryptText(encryptedEntryModel.getPassword().getCipherData(), masterpassword);
            entryModels.add(new EntryModel(encryptedEntryModel.getId(), encryptedEntryModel.getName(), decryptedUsername, decryptedPassword));
        }
        return entryModels;
    }

    public int addCategory(String name) {
       return database.addCategory(name);
    }

    public List<CategoryModel> getAllCategories() {
        return database.getAllCategories();
    }

    public boolean deleteCategory(int id, boolean deleteEntries) {
        ArrayList<EntryModel> entries = getAllEntries(id);
        if (deleteEntries) {
            for (EntryModel entry : entries) {
                removeEntryByID(entry.getId());
            }
        } else {
            // Set entry category_id to 0
            for (EntryModel entry : entries) {
                database.updateEntry(entry.getId(), entry.getName(), encryptionAlgorithm.encryptText(entry.getUsername(), masterpassword), encryptionAlgorithm.encryptText(entry.getPassword(), masterpassword), 0, "", "");
            }
        }

        return database.deleteCategory(id);
    }

   public boolean closeDatabase() {
       return database.close();
   }
}




