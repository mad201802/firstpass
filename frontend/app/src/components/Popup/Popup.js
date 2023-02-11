import React from "react";
import "./Popup.less";

import { CloseRounded } from "@mui/icons-material";
import { Button } from "components";
import useShortcut from "hooks/useShortcut";

/**
 * A custom popup
 * @param {object} props The props
 * @param {function} props.onClose The function to call when the popup is closed
 * @param {function} props.onSubmit The function to call when the popup is submitted
 * @param {string} props.submitText The text of the submit button
 * @param {string} props.closeText The text of the close button
 * @param {string} props.title The title of the popup
 * @param {string} props.type The type of the submit button
 * @param {string} props.size The size of the popup
 * @returns The popup component
 */
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
    useShortcut("Enter", (e) => {
        e.preventDefault();
        onSubmit();
    });

    return (
        <div className="popupRapper">
            <div className="popup" data-size={size}>
                <div id="closeButton" onClick={onClose}>
                    <CloseRounded id="close" />
                </div>
                <h1>{title}</h1>

                {children}

                <div className="buttonBox">
                    <Button type="tertiary" className="cancelButton" onClick={onClose}>
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
