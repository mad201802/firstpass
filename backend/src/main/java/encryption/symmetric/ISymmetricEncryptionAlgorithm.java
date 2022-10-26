package encryption.symmetric;

import encryption.symmetric.models.CipherText;

public interface ISymmetricEncryptionAlgorithm {
    public CipherText encryptText(String text, String password);
    public String decryptText(CipherText text, String password);
}
