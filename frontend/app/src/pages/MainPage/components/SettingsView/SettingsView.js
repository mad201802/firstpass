import React, { useContext, useState } from "react";
import "./SettingsView.less";
import { Button, EditableProp, FormInput, Popup } from "components";
import useShortcut from "hooks/useShortcut";
import AppContext from "contexts/App.context";
import Section from "./components/Section";
import ColorProp from "components/ColorProp/ColorProp";
import { ColorLensRounded, ShortcutRounded } from "@mui/icons-material";

const SettingsView = ({ setSettingsVisible }) => {

    const { settings, setSettings } = useContext(AppContext);
    const { theme } = settings;

    const [confirmReset, setConfirmReset] = useState(null);

    useShortcut("Escape", () => setSettingsVisible(false));

    function update(e) {
        setSettings(s => ({ ...s, [e.target.name]: e.target.value }));
    }
    function updateTheme(e) {
        setSettings(s => ({ ...s, theme: { ...s.theme, [e.target.name]: e.target.value } }));
    }


    function resetShortcuts(confirm=false) {
        if (!confirm) {
            setConfirmReset("Shortcuts");
            return;
        }
        setConfirmReset(null);
        setSettings(s => ({
            ...s,
            createCategoryShortcut: "Ctrl+Shift+N",
            createEntryShortcut: "Ctrl+N",
            editCategoryShortcut: "F2",
            searchShortcut: "Ctrl+F",
        }));
    }

    function resetTheme(confirm=false) {
        if (!confirm) {
            setConfirmReset("Appearance");
            return;
        }
        setConfirmReset(null);
        setSettings(s => ({
            ...s,
            theme: {
                primary: "#f5b302",
                primaryLight: "#fed053",
                primaryDark: "#f5a303",

                bg: "#1f232a",
                bgLight: "#2b2f36", 
                bgLighter: "#3b3f46",

                text: "#f5f5f5",
                textDark: "#b3b3b3",
            }
        }));
    }

    return (
        <div className="settingsView">
            <div className="settingsView-header">
                <h1>Settings</h1>
            </div>
            <div className="settingsView-content">
                {/* <FormInput label="Primary Color" value={primaryColor} onInput={e => setPrimaryColor(e.target.value)} /> */}

                <Section title="Appearance" icon={<ColorLensRounded />} open={true}>
                    <ColorProp label="Primary Color" value={theme.primary} name="primary" onInput={updateTheme} />
                    <ColorProp
                        label="Primary Color (Light)"
                        value={theme.primaryLight}
                        name="primaryLight"
                        onInput={updateTheme}
                    />
                    <ColorProp
                        label="Primary Color (Dark)"
                        value={theme.primaryDark}
                        name="primaryDark"
                        onInput={updateTheme}
                    />

                    <ColorProp label="Background Color" value={theme.bg} name="bg" onInput={updateTheme} />
                    <ColorProp label="Background Color (Light)" value={theme.bgLight} name="bgLight" onInput={updateTheme} />
                    <ColorProp label="Background Color (Lighter)" value={theme.bgLighter} name="bgLighter" onInput={updateTheme} />

                    <div className="resetButton">
                        <p onClick={() => resetTheme()}>Revert to default theme</p>
                    </div>
                </Section>

                <Section title="Shortcuts" icon={<ShortcutRounded />} open={true}>
                    <EditableProp icon={<p>Create Category</p>} value={settings.createCategoryShortcut} name="createCategoryShortcut" onUpdate={update} />
                    <EditableProp icon={<p>Create Entry</p>} value={settings.createEntryShortcut} name="createEntryShortcut" onUpdate={update} />
                    <EditableProp icon={<p>Edit Category</p>} value={settings.editCategoryShortcut} name="editCategoryShortcut" onUpdate={update} />
                    <EditableProp icon={<p>Search</p>} value={settings.searchShortcut} name="searchShortcut" onUpdate={update} />

                    <div className="resetButton">
                        <p onClick={() => resetShortcuts()}>Revert to default shortcuts</p>
                    </div>
                </Section>
            </div>
            {confirmReset && (
                <Popup type="danger" size="small" title={`Reset ${confirmReset}`} submitText="Reset" onClose={() => setConfirmReset(null)} onSubmit={
                    confirmReset === "Shortcuts" ? () => resetShortcuts(true) : () => resetTheme(true)
                }>
                    {<p>Reset section "{confirmReset}"?</p>}
                </Popup>
            )}
        </div>
    );
};

export default SettingsView;
