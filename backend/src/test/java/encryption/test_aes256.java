package encryption;

import encryption.symmetric.algos.AES256;
import encryption.symmetric.models.CipherText;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;

public class test_aes256 {

    @Test
    public void test_encryption() {
        String text = "hello";
        AES256 aes256 = new AES256("secretsalt", 1000);
        CipherText cipherText = aes256.encryptText(text, "password");
        Assert.assertNotEquals(text, cipherText.text);
    }
}
