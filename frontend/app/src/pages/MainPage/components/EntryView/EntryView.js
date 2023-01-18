import React, { useContext, useEffect } from "react";
import "./EntryView.less";
import { UrlLogo, Button, Popup } from "components";
import useShortcut from "hooks/useShortcut";
import { ChevronLeftRounded, LinkRounded, NotesRounded, PersonRounded, KeyRounded, CheckRounded, DeleteRounded } from "@mui/icons-material";
import EditableProp from "./EditableProp";
import backend from "backend";
import AppContext from "contexts/App.context";
import PasswordStrength from "components/PasswordStrength/PasswordStrength";

const EntryView = ({ entry, setCurrentEntry }) => {

    const { db, setDb } = useContext(AppContext);

    const [state, setState] = React.useState(entry);
    const [passwordLoaded, setPasswordLoaded] = React.useState(false);
    const [confirmPopup, setConfirmPopup] = React.useState(false);

    async function loadPassword() {
        try {
            const res = await backend.call({
                type: "GET_PASSWORD",
                data: { id: entry.id }
            });
            setState(s => ({ ...s, password: res.data.password }));
            console.log(res);
            setPasswordLoaded(true);
        } catch (e) {
            console.error(e);
        }
    }
    useEffect(() => {loadPassword()}, [entry]);

    function update(e) {
        setState(s => ({ ...s, [e.target.name]: e.target.value }));
    }

    async function deleteEntry(confirm=false) {
        if (!confirm) {
            setConfirmPopup(true);
            return;
        }
        setConfirmPopup(false);
        try {
            await backend.call({
                type: "DELETE_ENTRY",
                data: { id: entry.id }
            });
            setCurrentEntry(null);
        } catch (e) {
            console.error(e);
        }
    }

    async function updateEntry() {
        if (state.name === entry.name
            && state.url === entry.url
            && state.username === entry.username
            && state.password === entry.password
            && state.notes === entry.notes) return setCurrentEntry(null);
        try {
            const { data: newEntry } = await backend.call({
                type: "UPDATE_ENTRY",
                data: { id: entry.id, ...state, category: entry.category }
            });
            setDb(db => {
                const newDb = { ...db };
                newDb.entries = newDb.entries.map(e => e.id === entry.id ? { ...e, ...newEntry } : e);
                return newDb;
            });
            setCurrentEntry(null);
        } catch (e) {
            console.error(e);
        }
    }

    useShortcut("Escape", () => {
        setCurrentEntry(null);
    });

    return (
        <div className="entryView">

            <div className="entryView-header">
                <EditableProp name="name" title={true} value={state.name} onUpdate={update} icon={<UrlLogo entry={{...entry, url: state.url}} />} />
            </div>
            <div className="entryView-content">
                
                <EditableProp name="url" value={state.url} onUpdate={update} icon={<LinkRounded />} />
                <EditableProp name="username" value={state.username} onUpdate={update} icon={<PersonRounded />} />
                <EditableProp name="password" password={true} value={state.password || entry.password || ""} onUpdate={update} icon={<KeyRounded />} />
                {passwordLoaded && <PasswordStrength password={state.password || entry.password || ""}/>}
                <EditableProp name="notes" multiline={true} value={state.notes} onUpdate={update} icon={<NotesRounded />} />
            </div>
            <div className="entryView-footer">
                <Button className="backButton" onClick={() => setCurrentEntry(null)}>
                    <ChevronLeftRounded />
                    Back
                </Button>
                <Button className="deleteButton" onClick={() => deleteEntry()}>
                    <DeleteRounded />
                    Delete
                </Button>
                <Button className="applyButton" onClick={updateEntry}>
                    <CheckRounded />
                    Apply
                </Button>
            </div>
            {confirmPopup && (
                <Popup title="Delete Entry?" onClose={() => setConfirmPopup(false)} onSubmit={() => deleteEntry(true)} submitText="Delete" type="danger" size="small">
                    Are you sure you want to delete this entry?<br />
                    This action cannot be undone.
                </Popup>
            )}
        </div>
    );
};

export default EntryView;
