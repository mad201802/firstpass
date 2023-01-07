import React from "react";
import "./FormInput.less";

const Comp = ({ test }) => {
    return <FormInput />;
}

const FormInput = ({
    iconLeft,
    iconRight,
    placeholder,
    type,
    value,
    spellCheck,
    readOnly,
    onInput,
    className
}) => {
    return (
        <div className={className ? `formInput ${className}` : "formInput"} >
            {iconLeft}
            <input
                {...{ placeholder, type, value, spellCheck, readOnly, onInput }}
            />
            {iconRight}
        </div>
    );
};

export default FormInput;
