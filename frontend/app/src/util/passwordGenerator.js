function generatePassword({ length=20, useLower=true, useUpper=true, useNumbers=true, useSpecial=true } = {}) {

    let generatedPassword = "";
    const lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
    const uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const numberChars = "0123456789";
    const specialChars = "!@#%^&*";
    let possibleChars = "";

    if (useLower) possibleChars += lowercaseChars;
    if (useUpper) possibleChars += uppercaseChars;
    if (useNumbers) possibleChars += numberChars;
    if (useSpecial) possibleChars += specialChars;

    for (let i = 0; i < length; i++) {
        generatedPassword += possibleChars.charAt(Math.floor(Math.random() * possibleChars.length));
    }

    return generatedPassword;        
}

export default generatePassword;