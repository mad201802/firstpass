package encryption;

public interface IEncryptionAlgorithm {
    public String encrypt(String plainText);
    public String decrypt(String cipherText);
}
