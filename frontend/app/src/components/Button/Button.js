import React from "react"
import "./Button.less"

/**
 * A custom button
 * @param {object} props The props
 * @param {string} props.children The button's content
 * @param {string} props.type The button type
 * @param {string} props.className The button class name
 * @param {string} props.size The button size
 * @returns The button component
 */
const Button = ({ children, type, className, size, ...props }) => {
    const classes = `button ${className || ""}`;
    return (
        <button className={classes} data-type={type} data-size={size} {...props}>{children}</button>
    );
};

export default Button;