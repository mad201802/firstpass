import React, { useState } from "react";
import "./ColorProp.less";

const ColorProp = ({ label, name, value, onInput }) => {

    return (
        <div className="colorProp">
            <input type="color" name={name} value={value} onInput={onInput} />
            <p>{label}</p>
        </div>
    );
};

export default ColorProp;
