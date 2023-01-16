import React from "react";
import "./PasswordStrength.less";

const PasswordStrength = () => {
const strength= "weak"

  return (
    <div className="progressWrapper" data-strength={strength}>
      <div className="progressBar">
        <div className="progressBarFill"></div>
      </div>
        <div className="progressLable">{strength}</div>
    </div>
  );
};

export default PasswordStrength;
