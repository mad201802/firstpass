package io.firstpass.utils;

import java.util.Base64;

/**
 * Utility class for common operations
 */
public class Utils {

    public static boolean useLogging = true;

    public static void log(String message) {
        if(useLogging) System.out.println(message);
    }

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

    /**
     * Converts a byte array to a hex string
     * @param bytes The byte array to convert
     * @return The hex string
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
