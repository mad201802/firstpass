import React from "react"
import "./Button.less"

const Button = ({ children, type, className, ...props }) => {
    const classes = `button ${className || ""}`;
    return (
        <button className={classes} data-type={type} {...props}>{children}</button>
    );
};

export default Button;