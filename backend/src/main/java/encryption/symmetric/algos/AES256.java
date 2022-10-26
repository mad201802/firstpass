package encryption.symmetric.algos;

import encryption.symmetric.ISymmetricEncryptionAlgorithm;
import encryption.symmetric.models.CipherText;
import jdk.jshell.execution.Util;
import utils.Utils;

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

public class AES256 implements ISymmetricEncryptionAlgorithm {

    private byte[] _salt;
    private int _iterations;
    private SecretKeyFactory _secretKeyFactory;

    public AES256(String salt, int iterations) {
        this._salt = salt.getBytes(StandardCharsets.UTF_8);
        this._iterations = iterations;
        this._secretKeyFactory = this.getSecretKeyFactory();
    }

    @Override
    public CipherText encryptText(String text, String password) {
        CipherText cipherText = new CipherText();

        SecretKeySpec secret = this.generateExpandedKey(password);
        Cipher cipher = this.initEncryptionCipher(secret);
        cipherText.iv = Utils.bytesToBase64(this.generateRandomIV(cipher));
        try {
            byte[] ciphertext = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            cipherText.text = Utils.bytesToBase64(ciphertext);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return cipherText;
    }

    @Override
    public String decryptText(CipherText text, String password) {
        Cipher cipher = this.initDecryptionCipher(this.generateExpandedKey(password), new IvParameterSpec(Utils.base64ToBytes(text.iv)));
        try {
            return new String(cipher.doFinal(Utils.base64ToBytes(text.text)), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKeyFactory getSecretKeyFactory() {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Could not get Secret Key Factory");
            return null;
        }
    }

    private byte[] generateRandomIV(Cipher cipher) {
        AlgorithmParameters params = cipher.getParameters();
        try {
            return params.getParameterSpec(IvParameterSpec.class).getIV();
        } catch (InvalidParameterSpecException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKeySpec generateExpandedKey(String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), this._salt, this._iterations, 256);
        try {
            SecretKey secret = this._secretKeyFactory.generateSecret(spec);
            return new SecretKeySpec(secret.getEncoded(), "AES");
        } catch (InvalidKeySpecException ex) {
            System.out.println("Invalid key spec!");
        }
        return null;
    }

    private Cipher initEncryptionCipher(SecretKeySpec secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

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
