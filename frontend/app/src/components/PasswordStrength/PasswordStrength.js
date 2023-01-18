import backend from "backend";
import React, { useEffect, useState } from "react";
import "./PasswordStrength.less";

const PasswordStrength = ( {password, style}) => {
    const [strength, setStrength] = useState(0.0);

    useEffect(() => {
        if (password.length > 256) return;
        backend.call({
            type: "GET_ENTROPY",
            data: {
                passwordToCompute: password
            }
        }).then(res => {
            setStrength(res.data.entropy.toFixed(1));
        })
    }, [password]);

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
        <div className="progressWrapper" data-strength={strength_name} style={style}>
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
