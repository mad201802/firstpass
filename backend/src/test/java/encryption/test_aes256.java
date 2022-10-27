package encryption;

import encryption.symmetric.algos.AES256;
import encryption.symmetric.models.CipherText;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;

public class test_aes256 {

    @Test
    public void test_encryption() {
        String text = "hello";
        AES256 aes256 = new AES256("secretsalt", 1000);
        CipherText cipherText = aes256.encryptText(text, "password");
        String decrypted = aes256.decryptText(cipherText, "password");

        Assertions.assertNotEquals(text, cipherText.text);
        Assertions.assertEquals(text, decrypted);
    }
    @Test
    public void test_encryption_with_wrong_iv_length() {
        String text = "hello";
        AES256 aes256 = new AES256("secretsalt", 1000);
        CipherText cipherText = aes256.encryptText(text, "password");
        cipherText.iv = "";
        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            String decrypted = aes256.decryptText(cipherText, "password");
        });

        String expectedMessage = "Wrong IV length: must be 16 bytes long";
        String actualMessage = ex.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_encryption_with_wrong_iv_content() {
        String text = "hello";
        AES256 aes256 = new AES256("secretsalt", 1000);
        CipherText cipherText = aes256.encryptText(text, "password");
        cipherText.iv = "jkvTzRDW+rG3m8Y/Wz3F6Q==";

        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            String decrypted = aes256.decryptText(cipherText, "password");
        });

        String expectedMessage = "Given final block not properly padded. Such issues can arise if a bad key is used during decryption.";
        String actualMessage = ex.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test_encryption_with_wrong_password() {
        String text = "hello";
        AES256 aes256 = new AES256("secretsalt", 1000);
        CipherText cipherText = aes256.encryptText(text, "password");
        Exception ex = Assertions.assertThrows(RuntimeException.class, () -> {
            String decrypted = aes256.decryptText(cipherText, "not password");
        });

        String expectedMessage = "Given final block not properly padded. Such issues can arise if a bad key is used during decryption.";
        String actualMessage = ex.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

}
