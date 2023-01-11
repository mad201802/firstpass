import React, {useState} from "react";
import "./AddEntryPopup.less";
import {
    CloseRounded,
    LinkRounded,
    PersonRounded,
    KeyRounded
} from "@mui/icons-material";

import { Button, FormInput, Popup } from "components";

const AddEntryPopup = ({ setAddEntryPopupVisible }) => {

    const [username, setUsername] = useState("");
    const [url, setUrl] = useState("");
    const [password, setPassword] = useState("");

    function addEntry() {
        setAddEntryPopupVisible(false);
        // TODO: Add entry to database
        console.log(`Adding entry with url: ${url}, username: ${username}, password: ${password}`);
    }

    return (
        <Popup
            onClose={() => setAddEntryPopupVisible(false)}
            onSubmit={addEntry}
            title="Add Entry"
            submitText="Add"
        >
            <div class="addEntryInputs">
                <FormInput
                    placeholder="URL"
                    autoFocus={true}
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
                />
            </div>
        </Popup>
    );
};

export default AddEntryPopup;
