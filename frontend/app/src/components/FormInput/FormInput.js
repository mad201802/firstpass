import React from "react";
import "./FormInput.less";

// TODO: Add error property to jighlight input red, onChange callback, no internal state

/**
 * A custom form input
 * @param {object} props The props
 * @param {object} props.iconLeft The icon to show on the left of the input
 * @param {object} props.iconRight The icon to show on the right of the input
 * @param {string} props.placeholder The input placeholder
 * @param {string} props.type The input type
 * @param {string} props.name The input name
 * @param {string} props.value The input value
 * @param {boolean} props.spellCheck Whether to enable spell check or not
 * @param {boolean} props.readOnly Whether the input is read only or not
 * @param {function} props.onInput The function to call when the input is changed
 * @param {boolean} props.disabled Whether the input is disabled or not
 * @param {boolean} props.autoFocus Whether to auto focus the input or not
 * @param {string} props.className The input class name
 * @param {boolean} props.error Whether the input is in error state or not
 * @param {object} props.inputProps The input props
 * @param {object} props.props The rest of the props
 * @returns The form input component
 * @example
 * <FormInput
 *  iconLeft={<Icon />}
 *  iconRight={<Icon />}
 *  placeholder="Placeholder"
 *  type="text"
 *  name="name"
 *  value="value"
 *  spellCheck={false}
 *  readOnly={false}
 *  onInput={function}
 *  disabled={false}
 *  autoFocus={false}
 *  className="className"
 *  error={false}
 *  inputProps={{ ... }}
 *  {...props}
 * />
 */
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
