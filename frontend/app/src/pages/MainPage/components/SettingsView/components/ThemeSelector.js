import React, { useContext, useState, useEffect } from "react";
import "./ThemeSelector.less";

import { DropdownMenu, Popup, Tooltip } from "components";

import AppContext from "contexts/App.context";

import backend from "backend";
import { BrushRounded, ContentCopy, DeleteRounded, EditRounded, LoginRounded } from "@mui/icons-material";

import ThemeListContext from "contexts/ThemeList.context";
import useShortcut from "hooks/useShortcut";

const ThemeOption = ({ data, current }) => {
    const { themes, setThemes, editTheme, setEditTheme: setEditable } = useContext(ThemeListContext);
    const editable = current && editTheme;

    const [name, setName] = useState(data?.name);
    const inputRef = React.useRef();

    useEffect(() => {
        if (editable) inputRef.current.focus();
        else {
            backend.updateTheme(data.file, { name });
            setThemes(themes.map(t => (t.file === data.file ? { ...t, name } : t)));
        }
    }, [editable]);
    useEffect(() => {
        setName(data?.name);
    }, [data?.name]);

    function update(e) {
        setName(e.target.value);
    }

    useShortcut(
        "Enter",
        () => {
            setEditable(false);
        },
        editable
    );

    return (
        <div className="themeOption">
            <input
                ref={inputRef}
                type="text"
                value={name}
                readOnly={!editable}
                onInput={update}
                onClick={e => {
                    if (!editable) return;
                    e.stopPropagation();
                    e.target.select();
                }}
                onBlur={e => {
                    if (e.relatedTarget?.classList.contains("editButton")) return;
                    setEditable(false);
                }}
            />
            {!current && !data.file.startsWith("*") && <p>CUSTOM</p>}
        </div>
    );
};

const ThemeSelector = () => {
    const { settings, setSettings, theme, setTheme } = useContext(AppContext);

    const [themes, setThemes] = useState([]);
    const [selectedTheme, setSelectedTheme] = useState(null);
    const [editTheme, setEditTheme] = useState(false);
    const [confirmDelete, setConfirmDelete] = useState(false);

    useEffect(() => {
        backend.getThemes().then(res => {
            setThemes(res);
            setSelectedTheme(res.findIndex(t => t.file === settings.theme));
        });
    }, []);

    async function deleteTheme(confirm = false) {
        if (!confirm) {
            setConfirmDelete(true);
            return;
        }
        setConfirmDelete(false);
        const success = await backend.deleteTheme(settings.theme);
        if (!success) return;
        setThemes(t => t.filter(t => t.file !== settings.theme));
        setSelectedTheme(0);
        setSettings(s => ({ ...s, theme: "*default" }));
    }

    return (
        <div className="themeSelector">
            {/* <p className="label">Theme</p> */}

            <ThemeListContext.Provider value={{ themes, setThemes, editTheme, setEditTheme }}>
                <DropdownMenu
                    value={selectedTheme}
                    icon={<BrushRounded />}
                    onChange={(i, val) => {
                        setSelectedTheme(i);
                        setSettings(s => ({ ...s, theme: val.file }));
                    }}
                    placeholder={<div>Select a theme...</div>}
                    options={themes}
                    component={ThemeOption}
                />
            </ThemeListContext.Provider>

            <div className="themeButtonGroup">
                {!settings.theme.startsWith("*") && (
                    <>
                        <Tooltip label="Edit Theme">
                            <button
                                data-active={editTheme}
                                className="editButton"
                                onClick={() => setEditTheme(v => !v)}>
                                <EditRounded />
                            </button>
                        </Tooltip>
                        <Tooltip label="Delete Theme">
                            <button className="deleteButton" onClick={() => deleteTheme()}>
                                <DeleteRounded />
                            </button>
                        </Tooltip>
                    </>
                )}
                <Tooltip label="Duplicate Theme">
                    <button
                        className="duplicateButton"
                        onClick={async () => {
                            const theme = await backend.importTheme(settings.theme);
                            if (!theme) return;
                            setThemes(t => [...t, theme]);
                            setSelectedTheme(themes.length);
                            setSettings(s => ({ ...s, theme: theme.file }));
                        }}>
                        <ContentCopy />
                    </button>
                </Tooltip>
                <Tooltip label="Import Theme">
                    <button
                        className="importButton"
                        onClick={async () => {
                            const file = await backend.openFile({
                                filters: [backend.FileFilter.Theme],
                                buttonLabel: "Import",
                                title: "Import Theme",
                                multi: false,
                            });
                            if (!file) return;
                            const theme = await backend.importTheme(file[0]);
                            if (!theme) return;
                            setThemes(t => [...t, theme]);
                            setSelectedTheme(themes.length);
                            setSettings(s => ({ ...s, theme: theme.file }));
                        }}>
                        <LoginRounded style={{ transform: "scaleX(-1)" }} />
                    </button>
                </Tooltip>
            </div>
            {confirmDelete && (
                <Popup
                    size="small"
                    title="Delete Theme"
                    submitText="Delete"
                    type="danger"
                    onClose={() => setConfirmDelete(false)}
                    onSubmit={() => deleteTheme(true)}>
                    Are you sure you want to delete this theme?
                    <br />
                    This action cannot be undone.
                </Popup>
            )}
        </div>
    );
};

export default ThemeSelector;
