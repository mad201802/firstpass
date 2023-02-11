import React, { useState } from "react";
import "./ColorProp.less";

/**
 * A color property component
 * @param {object} props The props 
 * @param {string} props.label The label of the color property
 * @param {string} props.name The name of the color property
 * @param {string} props.value The value of the color property
 * @param {function} props.onInput The function to call when the color property is changed
 * @returns The color property component
 */
const ColorProp = ({ label, name, value, onInput }) => {

    return (
        <div className="colorProp">
            <input type="color" name={name} value={value} onInput={onInput} />
            <p>{label}</p>
        </div>
    );
};

export default ColorProp;
