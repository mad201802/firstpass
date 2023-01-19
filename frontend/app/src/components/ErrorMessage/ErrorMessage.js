import React from "react";
import "./ErrorMessage.less";
import { ErrorOutlineRounded } from "@mui/icons-material";

const ErrorMessage = ({ error, showCode = false }) => {
    return (
        <div className="errorMessage">
            {showCode ? <span>{error.code}</span> : <div className="errorIcon"><ErrorOutlineRounded /></div>}
            {error.message}
        </div>
    );
};

export default ErrorMessage;
