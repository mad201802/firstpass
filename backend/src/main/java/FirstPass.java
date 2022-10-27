import encryption.symmetric.algos.AES256;
import encryption.symmetric.models.CipherData;

public class FirstPass {

    public static void main(String[] args) {
        AES256 aes256 = new AES256("secretsalt", 1000);
        CipherData encrypted = aes256.encryptText("test", "test");
        System.out.println(encrypted.text);
        String decrypted = aes256.decryptText(encrypted, "test");
        System.out.println(decrypted);
    }

}
