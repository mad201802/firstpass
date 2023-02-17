package io.firstpass.logic;

public class StrengthAnalyzer {
    /**
     * Provides utilities to rate the strength of a provided password.
     */

    public enum PasswordStrength {
        VERY_WEAK(1),
        WEAK(2),
        MEDIUM(3),
        STRONG(4),
        VERY_STRONG(5);

        public final int value;
        PasswordStrength(int value) {
            this.value = value;
        }
    }

    /**
     *  Simple method to rate a password's strength based on its length,
     *  the amount of different categories of characters and a check if the password
     *  includes weak default passwords.
     * @param password
     * The password to rate.
     * @return
     * The password's strength as rated by the method (A value from the enum "PasswordStrength").
     */
    public double computeEntropy(String password) {
        // Iterate through all characters and count
        // them in categories
        int upChars = 0, lowChars = 0, digits = 0, special = 0;
        for(int i=0; i<password.length(); i++)
        {
            char ch = password.charAt(i);
            if(Character.isUpperCase(ch))
                upChars++;
            else if(Character.isLowerCase(ch))
                lowChars++;
            else if(Character.isDigit(ch))
                digits++;
            else
                special++;
        }

        int charsetSize = 0;

        // Add security points based on the number of special characters:
        if(upChars > 0) {
            charsetSize += 29;
        }
        if(lowChars > 0) {
            charsetSize += 29;
        }
        if (digits > 0) {
            charsetSize += 10;
        }
        if(special > 0) {
            charsetSize += 27;
        }


        // If passwort length >= 256, always set length to 256 to prevent calculation error.
        int length = Math.min(password.length(), 256);

        double numberOfCombinations = Math.pow(charsetSize, length);
        double entropy;
        entropy = Math.log(numberOfCombinations) / Math.log(2);
        return entropy;
    }

//    public PasswordStrength entropyToStrength(double entropy) {
//        if (entropy < 30) {
//            return PasswordStrength.VERY_WEAK;
//        } else if (entropy < 60) {
//            return PasswordStrength.WEAK;
//        } else if (entropy < 80) {
//            return PasswordStrength.STRONG;
//        } else {
//            return PasswordStrength.VERY_STRONG;
//        }
//    }

    
}