import React, { useEffect } from "react";
import "./Popup.less";

import { CloseRounded } from "@mui/icons-material";
import { Button } from "components";

const Popup = ({
    onClose,
    onSubmit,
    children,
    submitText = "Ok",
    closeText = "Cancel",
    title="Popup"
}) => {

    useEffect(() => {
        const cb = (e) => {
            if (e.key === "Escape") onClose();
            if (e.key === "Enter") onSubmit();
            //TODO: Do not submit when empty!
        };

        window.addEventListener("keydown", cb);
        return () => {
            window.removeEventListener("keydown", cb);
        };
    },[]);

    return (
        <div className="popupRapper">
            <div className="popup">
                <div id="closeButton" onClick={onClose}>
                    <CloseRounded id="close" />
                </div>
                <h1>{title}</h1>

                {children}

                <div className="buttonBox">
                    <Button className="cancelButton" onClick={onClose}>
                        {closeText}
                    </Button>
                    <Button className="submitButton" onClick={onSubmit}>
                        {submitText}
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default Popup;
