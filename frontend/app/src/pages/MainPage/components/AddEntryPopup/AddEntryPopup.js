import React, { useState } from "react";
import "./AddEntryPopup.less";
import { NameR, LinkRounded, PersonRounded, KeyRounded,FormatSizeRounded } from "@mui/icons-material";

import { Button, FormInput, Popup } from "components";
import PasswordStrength from "components/PasswordStrength/PasswordStrength";

const AddEntryPopup = ({ setAddEntryPopupVisible }) => {
    const [username, setUsername] = useState("");
    const [url, setUrl] = useState("");
    const [password, setPassword] = useState("");
    const [name, setName] = useState("");

    function addEntry() {
        setAddEntryPopupVisible(false);
        // TODO: Add entry to database
        console.log(`Adding entry with url: ${url}, username: ${username}, password: ${password}`);
    }

    return (
        <Popup onClose={() => setAddEntryPopupVisible(false)} onSubmit={addEntry} title="Add Entry" submitText="Add">
            <div className="addEntryInputs">
                <FormInput
                    placeholder="Name"
                    autoFocus={true}
                    iconLeft={<FormatSizeRounded />}
                    value={url}
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
                <PasswordStrength></PasswordStrength>
            </div>
        </Popup>
    );
};

export default AddEntryPopup;
