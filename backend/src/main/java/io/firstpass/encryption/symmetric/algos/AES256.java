package io.firstpass.encryption.symmetric.algos;

import io.firstpass.encryption.symmetric.ISymmetricEncryptionAlgorithm;
import io.firstpass.encryption.symmetric.models.CipherData;
import io.firstpass.utils.Utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

/**
 * AES 256 io.firstpass.encryption algorithm
 */
public class AES256 implements ISymmetricEncryptionAlgorithm {

    private final byte[] _salt;
    private final int _iterations;
    private final SecretKeyFactory _secretKeyFactory;

    /**
     * Constructor for AES256
     * @param salt Salt to use for io.firstpass.encryption
     * @param iterations Number of iterations to use for io.firstpass.encryption
     */
    public AES256(String salt, int iterations) {
        this._salt = salt.getBytes(StandardCharsets.UTF_8);
        this._iterations = iterations;
        this._secretKeyFactory = this.getSecretKeyFactory();
    }

    /**
     * Encrypts the given data
     * @param text The String to encrypt.
     * @param password The password to use for io.firstpass.encryption.
     * @return The encrypted data.
     */
    @Override
    public CipherData encryptText(String text, String password) {
        CipherData cipherData = new CipherData();

        SecretKeySpec secret = this.generateExpandedKey(password);
        Cipher cipher = this.initEncryptionCipher(secret);
        cipherData.iv = Utils.bytesToBase64(this.generateRandomIV(cipher));
        try {
            byte[] ciphertext = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            cipherData.text = Utils.bytesToBase64(ciphertext);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return cipherData;
    }

    /**
     * Decrypts the given data
     * @param data The data to decrypt.
     * @param password The password to use for decryption.
     * @return The decrypted data.
     */
    @Override
    public String decryptText(CipherData data, String password) {
        Cipher cipher = this.initDecryptionCipher(this.generateExpandedKey(password), new IvParameterSpec(Utils.base64ToBytes(data.iv)));
        try {
            return new String(cipher.doFinal(Utils.base64ToBytes(data.text)), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate a SecretKeyFactory for the AES256 algorithm with PD5WithHmacSHA256
     * @return The SecretKeyFactory
     */
    private SecretKeyFactory getSecretKeyFactory() {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException ex) {
            Utils.log("Could not get Secret Key Factory");
            return null;
        }
    }

    /**
     * Generates a random IV for io.firstpass.encryption based on the given cipher
     * @param cipher The cipher to use for io.firstpass.encryption
     * @return The generated IV
     */
    private byte[] generateRandomIV(Cipher cipher) {
        AlgorithmParameters params = cipher.getParameters();
        try {
            return params.getParameterSpec(IvParameterSpec.class).getIV();
        } catch (InvalidParameterSpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generate the expanded key for the given password
     * @param password The password to use for io.firstpass.encryption
     * @return The expanded key
     */
    private SecretKeySpec generateExpandedKey(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), this._salt, this._iterations, 256);
        try {
            SecretKey secret = this._secretKeyFactory.generateSecret(spec);
            return new SecretKeySpec(secret.getEncoded(), "AES");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the io.firstpass.encryption cipher
     * @param secret The KeySpecs for the chosen algorithm
     * @return The initialized cipher
     */
    private Cipher initEncryptionCipher(SecretKeySpec secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the cipher for decryption
     * @param secret The KeySpecs for the chosen algorithm
     * @param ivParameterSpec The IV to use for decryption
     * @return The initialized cipher
     */
    private Cipher initDecryptionCipher(SecretKeySpec secret, IvParameterSpec ivParameterSpec) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
}
