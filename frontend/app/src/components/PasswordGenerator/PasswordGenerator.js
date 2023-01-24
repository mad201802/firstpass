import React, { useState } from "react";
import "./PasswordGenerator.less";
import { Button } from "components";
import {SyncLockRounded} from "@mui/icons-material";


const PasswordGenerator = ({ onPasswordGenerate }) => {
    // useState hook to handle the state of the password generator

    // random number between 14 and 17
    const [passwordLength, setPasswordLength] = useState(Math.floor(Math.random() * 4) + 14);
    const [includeLowercase, setIncludeLowercase] = useState(true);
    const [includeUppercase, setIncludeUppercase] = useState(true);
    const [includeNumbers, setIncludeNumbers] = useState(true);
    const [includeSpecial, setIncludeSpecial] = useState(true);
    const [password, setPassword] = useState("");

    // function to handle form submission
    const generate = e => {
        e.preventDefault();
        // logic to generate the password
        let generatedPassword = "";
        const lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
        const uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        const numberChars = "0123456789";
        const specialChars = "!@#%^&*";
        let possibleChars = "";
        if (includeLowercase) possibleChars += lowercaseChars;
        if (includeUppercase) possibleChars += uppercaseChars;
        if (includeNumbers) possibleChars += numberChars;
        if (includeSpecial) possibleChars += specialChars;
        for (let i = 0; i < passwordLength; i++) {
            generatedPassword += possibleChars.charAt(Math.floor(Math.random() * possibleChars.length));
        }
        setPassword(generatedPassword);
        onPasswordGenerate(generatedPassword);
    };

    return (
        <form onSubmit={generate}>
            <Button className="generateButton" type="submit">{<SyncLockRounded/>}</Button>
        </form>
    );
};

export default PasswordGenerator;
