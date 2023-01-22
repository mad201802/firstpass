import React from "react";
import "./FormInput.less";

// TODO: Add error property to jighlight input red, onChange callback, no internal state

const FormInput = React.forwardRef(({
    iconLeft,
    iconRight,
    placeholder,
    type = "text",
    name,
    value,
    spellCheck = false,
    readOnly = false,
    onInput,
    disabled = false,
    autoFocus = false,
    className,
    error = false,
    inputProps,
    ...props
}, ref) => {
    let classes = "formInput";
    if (error) classes += " error";
    if (className) classes += ` ${className}`;

    return (
        <div className={classes} {...props}>
            {iconLeft}
            <input
                {...{ name, placeholder, type, value, spellCheck, readOnly, onInput, autoFocus, disabled, ref, ...inputProps }}
            />
            {iconRight}
        </div>
    );
});

export default FormInput;
