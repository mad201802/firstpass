import React from "react";
import "./CheckBoxProp.less";
import { CheckRounded } from "@mui/icons-material";

/**
 * A checkbox Property component
 * @param {object} props The props
 * @param {string} props.label The label of the checkbox
 * @param {string} props.name The name of the checkbox
 * @param {boolean} props.value The value of the checkbox
 * @param {function} props.onInput The function to call when the checkbox is clicked 
 * @returns The checkbox property component
 */
const CheckBoxProp = ({ label, name, value, onInput }) => {
    return (
        <div className="checkBoxProp" onClick={
            () => onInput({ target: { name, value: !value } })
        }>
            <div className="checkBox" data-checked={value}>
                {value && <CheckRounded />}
            </div>
            <p>{label}</p>
        </div>
    );
};

export default CheckBoxProp;
