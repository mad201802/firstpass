package utils;

import java.util.Base64;

/**
 * Utility class for common operations
 */
public class Utils {

    /**
     * Converts a byte array to a Base64 encoded string
     * @param bytes The byte array to convert
     * @return The Base64 encoded string
     */
    public static String bytesToBase64(byte[] bytes) {
        byte[] encoded = Base64.getEncoder().encode(bytes);
        return new String(encoded);
    }

    /**
     * Converts a Base64 encoded string to a byte array
     * @param base64String The Base64 encoded string to convert
     * @return The byte array
     */
    public static byte[] base64ToBytes(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

}
