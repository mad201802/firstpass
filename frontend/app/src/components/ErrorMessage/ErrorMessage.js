import React from "react";
import "./ErrorMessage.less";
import { ErrorOutlineRounded } from "@mui/icons-material";

/**
 * A custom error message
 * @param {object} props The props
 * @param {object} props.error The error object
 * @param {string} props.error.code The error code
 * @param {string} props.error.message The error message
 * @param {boolean} props.showCode Whether to show the error code or not
 * @returns The error message component
 */
const ErrorMessage = ({ error, showCode = false }) => {
    return (
        <div className="errorMessage">
            {showCode ? <span>{error.code}</span> : <div className="errorIcon"><ErrorOutlineRounded /></div>}
            {error.message}
        </div>
    );
};

export default ErrorMessage;
