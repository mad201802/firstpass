import React from "react"
import "./Button.less"

const Button = ({ children, className, ...props }) => {
    const classes = `button ${className || ""}`;
    return (
        <button className={classes} {...props}>{children}</button>
    );
};

export default Button;