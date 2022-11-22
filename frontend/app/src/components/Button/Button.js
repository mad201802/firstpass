import React from "react"
import "./Button.less"

const Button = ({ children, ...props }) => {
    return (
        <button className="button" {...props}>{children}</button>
    );
};

export default Button;