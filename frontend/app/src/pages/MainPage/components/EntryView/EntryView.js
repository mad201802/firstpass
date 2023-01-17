import React from "react";
import "./EntryView.less";
import { UrlLogo, Button, FormInput } from "components";
import { ChevronLeftRounded } from "@mui/icons-material";

const EntryView = ({ entry, setCurrentEntry }) => {

    const [state, setState] = React.useState({
        name: entry.name,
        url: entry.url,
        username: entry.username,
        password: entry.password,
        notes: entry.notes,
    });

    function update(e) {
        setState(s => ({ ...s, [e.target.name]: e.target.value }));
    }


    return (
        <div className="entryView">

            <div className="entryView-header">
                <Button className="backButton" onClick={() => setCurrentEntry(null)}>
                    <ChevronLeftRounded />
                    Back
                </Button>
                {/* <div className="entryViewHeaderInfo">
                    <UrlLogo entry={entry} className="entryView-header-logo" />
                    <div className="entryViewHeaderInfo-name">{entry.name}</div>
                </div> */}
            </div>
            <div className="entryView-content">
                <FormInput name="name" value={state.name} onInput={update}/>
                <FormInput name="url" value={state.url} onInput={update}/>
                <FormInput name="username" value={state.username} onInput={update}/>
                <FormInput name="password" value={state.password} onInput={update}/>
                <FormInput name="notes" value={state.notes} onInput={update}/>
            </div>
        </div>
    );
};

export default EntryView;
