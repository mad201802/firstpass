import React, { useEffect } from "react";
import "./SettingsView.less";
import { FormInput } from "components";
import useShortcut from "hooks/useShortcut";

const SettingsView = ({ setSettingsVisible }) => {

    const [primaryColor, setPrimaryColor] = React.useState(localStorage.getItem("primaryColor") || "f5b302");

    useEffect(() => {
        localStorage.setItem("primaryColor", primaryColor);
        document.body.style.setProperty("--primary", "#" + primaryColor);
    }, [primaryColor]);

    useShortcut("Escape", () => setSettingsVisible(false));

    return (
        <div className="settingsView">
            <div className="settingsViewHeader">
                <h1>Settings</h1>
            </div>
            <div className="settingsViewContent">
                <FormInput label="Primary Color" value={primaryColor} onInput={e => setPrimaryColor(e.target.value)} />
            </div>
        </div>
    );
};

export default SettingsView;
