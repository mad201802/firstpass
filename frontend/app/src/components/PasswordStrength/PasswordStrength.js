import React from "react";
import "./PasswordStrength.less";

const PasswordStrength = () => {
    const strength = 29;
    let strength_name;
    if (strength < 15){
        strength_name = "very weak";
    }
    else if (strength < 30){
        strength_name = "weak";
    }
    else if (strength < 60){
        strength_name = "medium";
    }
    else if (strength < 80){
        strength_name = "strong";
    }
    else {
        strength_name = "very strong";
    }

    return (
        <div className="progressWrapper" data-strength={strength_name}>
            <div className="progressBar">
                <div className="progressBarFill" style={{ width: `${strength / 80 * 100}%` }}></div>
            </div>
            <div className="progressLabels">
                <div>Strength: {strength_name}</div>
                <div>Entropy: {strength}</div>
            </div>
        </div>
    );
};

export default PasswordStrength;
