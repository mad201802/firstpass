import React from "react";
import "./CheckBoxProp.less";
import { CheckRounded } from "@mui/icons-material";

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
