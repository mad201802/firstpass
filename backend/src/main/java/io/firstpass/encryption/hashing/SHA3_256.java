package io.firstpass.encryption.hashing;

import io.firstpass.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA3_256 {

    /**
     * Hashes the input using SHA3-256
     * @param input The input to hash
     * @return The hashed input
     */
    public static String hash(String input) {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        final byte[] hashbytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Utils.bytesToHex(hashbytes);
    }
}
