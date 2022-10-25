package logic;

public class StrengthAnalyzer {

    static enum PasswordStrength {
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

    public PasswordStrength checkStrength(String password) {
        /*
        Requirements for a safe password:
        - Does not contain a char sequence from the unsafePasswords array
        - Has more than 6 characters, better 8.
        - Contains at least 1 digit, 1 uppercase char, 1 lowercase char, 1 special char

        Very Strong: >10
        Strong: 7-10
        Weak: 3-6
        Very Weak: 0-2

         */

        String[] unsafePasswords ={ "12345","123456","123456789","test1","password","12345678","zinch","g_czechout","asdf","qwerty","1234567890","1234567","Aa123456.","iloveyou","1234","abc123","111111","123123","dubsmash","test","princess","qwertyuiop","sunshine","BvtTest123","11111","ashley","00000","000000","password1","monkey","livetest","55555","soccer","charlie","asdfghjkl","654321","family","michael","123321","football","baseball","q1w2e3r4t5y6","nicole","jessica","purple","shadow","hannah","chocolate","michelle","daniel","maggie","qwerty123","hello","112233","jordan","tigger","666666","987654321","superman","12345678910","summer","1q2w3e4r5t","fitness","bailey","zxcvbnm","fuckyou","121212","buster","butterfly","dragon","jennifer","amanda","justin","cookie","basketball","shopping","pepper","joshua","hunter","ginger","matthew","abcd1234","taylor","samantha","whatever","andrew","1qaz2wsx3edc","thomas","jasmine","animoto","madison","0987654321","54321","flower","Password","maria","babygirl","lovely","sophie","Chegg123"};
        int strength = 0;

        // Check if password contains an unsafe password from the array.
        // If so, return 0;
        for (String unsafePassword : unsafePasswords) {
            if (password.contains(unsafePassword)) {
                return PasswordStrength.VERY_WEAK;
            }
        }

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

        // Check length of password
        if(password.length() >= 12) {
            strength = strength+6;
        }
        else if(password.length() >= 8) {
            strength = strength+3;
        }
        else if(password.length() >= 6) {
            strength = strength+1;
        }

        // Add security points based on the number of special characters:
        if((upChars > 0) && (lowChars > 0) && (digits > 1) && (special > 1)) {
            strength = strength+5;
        }
        else if((upChars > 0) && (lowChars > 0) && (digits > 0) && (special > 0)) {
            strength = strength+3;
        }
        else if ((upChars > 0) || (lowChars > 0) || (digits > 0) || (special > 0)) {
            strength = strength+3;
        }

        if (strength < 3) {
            return PasswordStrength.VERY_WEAK;
        } else if (strength < 7) {
            return PasswordStrength.WEAK;
        } else if (strength < 11) {
            return PasswordStrength.STRONG;
        } else {
            return PasswordStrength.VERY_STRONG;
        }

    }
}
