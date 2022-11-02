package encryption.symmetric;

import encryption.symmetric.models.CipherData;

/**
 * Interface for symmetric encryption algorithms.
 */
public interface ISymmetricEncryptionAlgorithm {
    /**
     * Encrypts the given data.
     * @param text The String to encrypt.
     * @param password The password to use for encryption.
     * @return The encrypted data.
     */
    public CipherData encryptText(String text, String password);

    /**
     * Decrypts the given data.
     * @param data The data to decrypt.
     * @param password The password to use for decryption.
     * @return The decrypted data.
     */
    public String decryptText(CipherData data, String password);
}
