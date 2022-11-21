package io.firstpass.manager;

import io.firstpass.database.models.EncryptedEntryModel;
import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.database.IEncryptedDatabase;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.manager.models.EntryModel;

import java.util.ArrayList;

public class PasswordManager {

    IEncryptedDatabase database;
    ISymmetricEncryptionAlgorithm encryptionAlgorithm;
    String masterpassword;

    public PasswordManager(IEncryptedDatabase database, ISymmetricEncryptionAlgorithm encryptionAlgorithm, String masterpassword) {
        this.database = database;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.masterpassword = masterpassword;
    }

    public int addEntry(String name,String username, String password, int category_id) {
        CipherData encryptedUsername = encryptionAlgorithm.encryptText(username, masterpassword);
        CipherData encryptedPassword = encryptionAlgorithm.encryptText(password, masterpassword);
        return database.addEntry(name, encryptedUsername, encryptedPassword, category_id);
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
}




