import encryption.symmetric.algos.AES256;
import encryption.symmetric.models.CipherText;

import java.nio.charset.StandardCharsets;

public class FirstPass {

    public static void main(String[] args) {
        AES256 aes256 = new AES256("secretsalt", 1000);
        CipherText encrypted = aes256.encryptText("test", "test");
        System.out.println(encrypted.text);
        String decrypted = aes256.decryptText(encrypted, "test");
        System.out.println(decrypted);
    }

}
