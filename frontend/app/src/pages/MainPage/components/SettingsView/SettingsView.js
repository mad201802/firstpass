import React, { useEffect } from "react";
import "./SettingsView.less";

const SettingsView = ({ setSettingsVisible }) => {

    useEffect(() => {
        const cb = (e) => {
            if (e.key === "Escape")
                setSettingsVisible(false);
        }
        document.addEventListener("keydown", cb);

        return () => {
            document.removeEventListener("keydown", cb);
        }
    })

    return (
        <div className="settingsView">
            <div className="settingsViewHeader">
                <h1>Settings</h1>
            </div>
            <div className="settingsViewContent">
                <div className="themeOption">
                    <h2>Theme</h2>
                </div>
            </div>
        </div>
    );
};

export default SettingsView;
