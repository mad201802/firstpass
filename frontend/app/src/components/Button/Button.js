import React from "react"
import "./Button.less"

const Button = ({ children, type, className, size, ...props }) => {
    const classes = `button ${className || ""}`;
    return (
        <button className={classes} data-type={type} data-size={size} {...props}>{children}</button>
    );
};

export default Button;