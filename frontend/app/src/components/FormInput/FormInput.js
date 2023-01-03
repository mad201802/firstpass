import React from "react";
import "./FormInput.less";

// TODO: Add error property to jighlight input red, onChange callback, no internal state

const FormInput = ({
    iconLeft,
    iconRight,
    placeholder,
    type = "text",
    value,
    spellCheck = false,
    readOnly = false,
    onInput,
    autoFocus = false,
    className,
    error = false
}) => {
    let classes = "formInput";
    if (error) classes += " error";
    if (className) classes += ` ${className}`;

    return (
        <div className={classes}>
            {iconLeft}
            <input
                {...{ placeholder, type, value, spellCheck, readOnly, onInput, autoFocus }}
            />
            {iconRight}
        </div>
    );
};

export default FormInput;
