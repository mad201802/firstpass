package io.firstpass;

import io.firstpass.database.drivers.SQLiteDriver;
import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;

import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.utils.CLI;

import java.sql.SQLException;

public class FirstPass {

    public static void main(String[] args) throws SQLException {
        SQLiteDriver sqLiteDriver = new SQLiteDriver("test.db");


        ISymmetricEncryptionAlgorithm aes256 = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");
        CipherData username = aes256.encryptText("user1", "test");
        CipherData password = aes256.encryptText("password123", "test");

        sqLiteDriver.addEntry("Netflix", username, password, -9);
    }

}
