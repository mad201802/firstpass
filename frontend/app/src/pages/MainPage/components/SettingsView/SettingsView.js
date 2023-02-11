import React, { useContext, useEffect, useState } from "react";
import "./SettingsView.less";
import { Button, CheckBoxProp, DropdownMenu, EditableProp, FormInput, Popup } from "components";
import useShortcut from "hooks/useShortcut";
import AppContext from "contexts/App.context";
import Section from "./components/Section";
import { ColorProp } from "components";
import { ColorLensRounded, ShortcutRounded, TuneRounded } from "@mui/icons-material";
import backend from "backend";
import ThemeSelector from "./components/ThemeSelector";


const SettingsView = () => {

    const { settings, setSettings, theme, setTheme } = useContext(AppContext);
    const [confirmReset, setConfirmReset] = useState(null);

    function update(e) {
        setSettings(s => ({ ...s, [e.target.name]: e.target.value }));
    }
    function updateTheme(e) {
        setTheme(t => {
            const res = { ...t, [e.target.name]: e.target.value };
            backend.updateTheme(settings.theme, res);
            return res;
        });
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

    return (
        <div className="settingsView">
            <div className="settingsView-header">
                <h1>Settings</h1>
            </div>
            <div className="settingsView-content">

                <Section title="Theme" icon={<ColorLensRounded />} open={true}>
                    <ThemeSelector />

                    {!settings.theme.startsWith("*") ? (
                    <>
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
                        <ColorProp
                            label="Background Color (Light)"
                            value={theme.bgLight}
                            name="bgLight"
                            onInput={updateTheme}
                        />
                        <ColorProp
                            label="Background Color (Lighter)"
                            value={theme.bgLighter}
                            name="bgLighter"
                            onInput={updateTheme}
                        />
                        <ColorProp label="Divider Color" value={theme.divider} name="divider" onInput={updateTheme} />

                        <ColorProp label="Text Color" value={theme.text} name="text" onInput={updateTheme} />
                        <ColorProp label="Text Color (Dark)" value={theme.textDark} name="textDark" onInput={updateTheme} />
                        <ColorProp
                            label="Text Color (Inverted)"
                            value={theme.textInv}
                            name="textInv"
                            onInput={updateTheme}
                        />
                    </>) : (
                        <p style={{ padding: "10px", opacity: 0.5, fontSize: "12px" }}>To edit this theme, duplicate it using the buttons above.</p>
                    )}
                </Section>

                <Section title="General" icon={<TuneRounded />} open={true}>
                    <CheckBoxProp
                        label="Load Favicons"
                        value={settings.loadFavicons}
                        name="loadFavicons"
                        onInput={update}
                    />
                    <CheckBoxProp
                        label="Compact List View"
                        value={settings.compactView}
                        name="compactView"
                        onInput={update}
                    />
                    <CheckBoxProp
                        label="Show Sensitive Info in List View"
                        value={settings.showSensitiveInfo}
                        name="showSensitiveInfo"
                        onInput={update}
                    />
                </Section>

                <Section title="Shortcuts" icon={<ShortcutRounded />} open={true}>
                    <EditableProp
                        icon={<p>Create Category</p>}
                        value={settings.createCategoryShortcut}
                        name="createCategoryShortcut"
                        onUpdate={update}
                    />
                    <EditableProp
                        icon={<p>Create Entry</p>}
                        value={settings.createEntryShortcut}
                        name="createEntryShortcut"
                        onUpdate={update}
                    />
                    <EditableProp
                        icon={<p>Edit Category</p>}
                        value={settings.editCategoryShortcut}
                        name="editCategoryShortcut"
                        onUpdate={update}
                    />
                    <EditableProp
                        icon={<p>Search</p>}
                        value={settings.searchShortcut}
                        name="searchShortcut"
                        onUpdate={update}
                    />

                    <div className="resetButton">
                        <p onClick={() => resetShortcuts()}>Revert to default shortcuts</p>
                    </div>
                </Section>
            </div>
            {confirmReset && (
                <Popup
                    type="danger"
                    size="small"
                    title={`Reset ${confirmReset}`}
                    submitText="Reset"
                    onClose={() => setConfirmReset(null)}
                    onSubmit={confirmReset === "Shortcuts" ? () => resetShortcuts(true) : null}>
                    {<p>Reset section "{confirmReset}"?</p>}
                </Popup>
            )}
        </div>
    );
};

export default SettingsView;