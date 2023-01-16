import React, { useState, useContext } from "react";
import "./AddEntryPopup.less";
import { NameR, LinkRounded, PersonRounded, KeyRounded,FormatSizeRounded } from "@mui/icons-material";

import { Button, FormInput, Popup } from "components";
import PasswordStrength from "components/PasswordStrength/PasswordStrength";

import backend from "backend";
import AppContext from "contexts/App.context";

const AddEntryPopup = ({ setAddEntryPopupVisible, currentCategory}) => {
    const [username, setUsername] = useState("");
    const [url, setUrl] = useState("");
    const [password, setPassword] = useState("");
    const [name, setName] = useState("");

    const { setDb } = useContext(AppContext);

    async function addEntry() {
        setAddEntryPopupVisible(false);
        const { data: entry } = await backend.call({
            type: "CREATE_ENTRY",
            data:{ 
                name: name,
                url: url,
                username: username,
                password: password,
                notes: "",
                category_id: currentCategory
            }
        });
        setDb(db => {
            const newDb = { ...db };
            newDb.categories[currentCategory].entries.push(entry);
            return newDb;
        });
        console.log(`Adding entry with url: ${url}, username: ${username}, password: ${password}`);
    }

    return (
        <Popup onClose={() => setAddEntryPopupVisible(false)} onSubmit={addEntry} title="Add Entry" submitText="Add">
            <div className="addEntryInputs">
                <FormInput
                    placeholder="Name"
                    autoFocus={true}
                    iconLeft={<FormatSizeRounded />}
                    value={name}
                    onInput={e => setName(e.target.value)}
                />
                <FormInput
                    placeholder="URL"
                    iconLeft={<LinkRounded />}
                    value={url}
                    onInput={e => setUrl(e.target.value)}
                />
                <FormInput
                    placeholder="Username"
                    iconLeft={<PersonRounded />}
                    value={username}
                    onInput={e => setUsername(e.target.value)}
                />
                <FormInput
                    placeholder="Password"
                    iconLeft={<KeyRounded />}
                    value={password}
                    onInput={e => setPassword(e.target.value)}
                    type="password"
                />
                <PasswordStrength password={password} ></PasswordStrength>
            </div>
        </Popup>
    );
};

export default AddEntryPopup;
