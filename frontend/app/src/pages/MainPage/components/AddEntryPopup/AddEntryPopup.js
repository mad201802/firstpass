import React, { useState, useContext, useRef } from "react";
import "./AddEntryPopup.less";
import { LinkRounded, PersonRounded, KeyRounded, FormatSizeRounded, SyncLockRounded } from "@mui/icons-material";

import { FormInput, Popup, PasswordStrength, Button } from "components";

import backend from "backend";
import AppContext from "contexts/App.context";
import generatePassword from "util/passwordGenerator";

const AddEntryPopup = ({ setAddEntryPopupVisible, currentCategory }) => {

    const [state, setState] = useState({
        name: "",
        url: "",
        username: "",
        password: "",
    });
    const stateRef = useRef();
    stateRef.current = state;

    function update(e) {
        setState(s => ({ ...s, [e.target.name]: e.target.value }));
        // Write generated password to password field
    
    }

    const { setDb } = useContext(AppContext);

    async function addEntry() {
        setAddEntryPopupVisible(false);
        let { data: entry } = await backend.call({
            type: "CREATE_ENTRY",
            data: {
                ...stateRef.current,
                notes: "",
                category: currentCategory,
            },
        });
        setDb(db => {
            const newDb = { ...db };
            newDb.entries.push(entry);
            return newDb;
        });
    }

    return (
        <Popup onClose={() => setAddEntryPopupVisible(false)} onSubmit={addEntry} title="Add Entry" submitText="Add">
            <div className="addEntryInputs">
                <FormInput
                    placeholder="Name"
                    name="name"
                    autoFocus={true}
                    iconLeft={<FormatSizeRounded />}
                    value={state.name}
                    onInput={update}
                />
                <FormInput placeholder="URL" name="url" iconLeft={<LinkRounded />} value={state.url} onInput={update} />
                <FormInput
                    placeholder="Username"
                    name="username"
                    iconLeft={<PersonRounded />}
                    value={state.username}
                    onInput={update}
                />
                <div className="passwordInput">
                <FormInput
                    placeholder="Password"
                    name="password"
                    iconLeft={<KeyRounded />}
                    value={state.password}
                    onInput={update}
                    type="password"
                />
                <Button className="generateButton" onClick={() => {
                    setState(s => ({ ...s, password: generatePassword() }));
                }}>{<SyncLockRounded/>}</Button>
                </div>
                <PasswordStrength password={state.password}></PasswordStrength>
            </div>
        </Popup>
    );
};

export default AddEntryPopup;
