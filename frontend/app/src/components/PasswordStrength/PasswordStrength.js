import React from "react";
import "./PasswordStrength.less";

const PasswordStrength = () => {
const strength= "strong"

  return (
    <div className="progressWrapper" data-strength={strength}>
        <div className="progressLable">Password Strength: {strength}</div>
      <div className="progressBar">
        <div className="progressBarFill"></div>
      </div>
    </div>
  );
};

export default PasswordStrength;
