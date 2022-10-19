package encryption.exceptions;

public class EncryptionAlgorithmNotFound extends Exception {
    public EncryptionAlgorithmNotFound(String message) {
        super(message);
    }

    public EncryptionAlgorithmNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
