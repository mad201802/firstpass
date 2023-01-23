import React from "react";
import "./Popup.less";

import { CloseRounded } from "@mui/icons-material";
import { Button } from "components";
import useShortcut from "hooks/useShortcut";

const Popup = ({
    onClose,
    onSubmit,
    children,
    submitText = "Ok",
    closeText = "Cancel",
    title="Popup",
    type="default",
    size="default",
}) => {

    useShortcut("Escape", () => onClose());
    useShortcut("Enter", () => onSubmit());

    return (
        <div className="popupRapper">
            <div className="popup" data-size={size}>
                <div id="closeButton" onClick={onClose}>
                    <CloseRounded id="close" />
                </div>
                <h1>{title}</h1>

                {children}

                <div className="buttonBox">
                    <Button className="cancelButton" onClick={onClose}>
                        {closeText}
                    </Button>
                    <Button className="submitButton" onClick={onSubmit} type={type}>
                        {submitText}
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default Popup;
