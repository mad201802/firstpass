import React, { useState, useContext, useRef } from "react";
import "./AddEntryPopup.less";
import { NameR, LinkRounded, PersonRounded, KeyRounded,FormatSizeRounded } from "@mui/icons-material";

import { Button, FormInput, Popup } from "components";
import PasswordStrength from "components/PasswordStrength/PasswordStrength";

import backend from "backend";
import AppContext from "contexts/App.context";

const AddEntryPopup = ({ setAddEntryPopupVisible, currentCategory}) => {
    // const [username, setUsername] = useState("");
    // const [url, setUrl] = useState("");
    // const [password, setPassword] = useState("");
    // const [name, setName] = useState("");

    const [state, setState] = useState({
        name: "",
        url: "",
        username: "",
        password: "",
    });
    const stateRef = useRef();
    stateRef.current = state;

    function update(e) {
        setState(s => ({...s, [e.target.name]: e.target.value}));
    }


    const { setDb } = useContext(AppContext);

    async function addEntry() {
        setAddEntryPopupVisible(false);
        const { data: entry } = await backend.call({
            type: "CREATE_ENTRY",
            data:{ 
                ...stateRef.current,
                notes: "",
                category_id: currentCategory
            }
        });
        console.log("from backend:", entry);
        setDb(db => {
            const newDb = { ...db };
            newDb.entries.push({...entry, category: currentCategory, url:"https://pornhub.com"});
            return newDb;
        });
        console.log(`Adding entry with url: ${stateRef.current.url}, username: ${stateRef.current.username}, password: ${stateRef.current.password}`);
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
                <FormInput
                    placeholder="URL"
                    name="url"
                    iconLeft={<LinkRounded />}
                    value={state.url}
                    onInput={update}
                />
                <FormInput
                    placeholder="Username"
                    name="username"
                    iconLeft={<PersonRounded />}
                    value={state.username}
                    onInput={update}
                />
                <FormInput
                    placeholder="Password"
                    name="password"
                    iconLeft={<KeyRounded />}
                    value={state.password}
                    onInput={update}
                    type="password"
                />
                <PasswordStrength password={state.password} ></PasswordStrength>
            </div>
        </Popup>
    );
};

export default AddEntryPopup;
