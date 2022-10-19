package encryption;

import encryption.algos.AES256;
import encryption.exceptions.EncryptionAlgorithmNotFound;

public class EncryptionFactory {

public static IEncryptionAlgorithm getEncryptionAlgorithm(String algorithm) throws EncryptionAlgorithmNotFound {
        switch (algorithm) {
            case "AES256":
                return new AES256();
            default:
                throw new EncryptionAlgorithmNotFound("Encryption algorithm not found");
        }
    }
}
