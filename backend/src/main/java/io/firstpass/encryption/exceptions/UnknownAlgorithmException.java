package io.firstpass.encryption.exceptions;

public class UnknownAlgorithmException extends RuntimeException {

    /**
     * Creates a new UnknownAlgorithmException
     * @param message The message to display.
     */
    public UnknownAlgorithmException(String message) {
        super(message);
    }
}
