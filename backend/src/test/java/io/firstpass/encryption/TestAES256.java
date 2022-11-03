package io.firstpass.encryption;

import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.SymmetricEncryptionFactory;
import io.firstpass.encryption.symmetric.models.CipherData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAES256 {

    @Test
    public void test_encryption() {
        String text = "hello";
        ISymmetricEncryptionAlgorithm aes256 = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");
        CipherData cipherData = aes256.encryptText(text, "password");
        String decrypted = aes256.decryptText(cipherData, "password");

        Assertions.assertNotEquals(text, cipherData.text);
        Assertions.assertEquals(text, decrypted);
    }
    @Test
    public void test_encryption_with_wrong_iv_length() {
        String text = "hello";
        ISymmetricEncryptionAlgorithm aes256 = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");
        CipherData cipherData = aes256.encryptText(text, "password");
        cipherData.iv = "";
        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            String decrypted = aes256.decryptText(cipherData, "password");
        });

        String expectedMessage = "Wrong IV length: must be 16 bytes long";
        String actualMessage = ex.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_encryption_with_wrong_iv_content() {
        String text = "hello";
        ISymmetricEncryptionAlgorithm aes256 = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");
        CipherData cipherData = aes256.encryptText(text, "password");
        cipherData.iv = "jkvTzRDW+rG3m8Y/Wz3F6Q==";

        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            String decrypted = aes256.decryptText(cipherData, "password");
        });

        String expectedMessage = "Given final block not properly padded. Such issues can arise if a bad key is used during decryption.";
        String actualMessage = ex.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_encryption_with_wrong_password() {
        String text = "hello";
        ISymmetricEncryptionAlgorithm aes256 = SymmetricEncryptionFactory.getSymmetricEncryption("aes256");
        CipherData cipherData = aes256.encryptText(text, "password");
        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            String decrypted = aes256.decryptText(cipherData, "not password");
        });

        String expectedMessage = "Given final block not properly padded. Such issues can arise if a bad key is used during decryption.";
        String actualMessage = ex.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
