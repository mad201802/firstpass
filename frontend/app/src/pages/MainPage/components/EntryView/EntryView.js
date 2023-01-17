import React from "react";
import "./EntryView.less";
import { UrlLogo, Button } from "components";
import { ChevronLeftRounded } from "@mui/icons-material";

const EntryView = ({ entry, setCurrentEntry }) => {
    return (
        <div className="entryView">

            <div className="entryView-header">
                <Button className="backButton" onClick={() => setCurrentEntry(null)}>
                    <ChevronLeftRounded />
                    Back
                </Button>
                <UrlLogo entry={entry} className="entryView-header-logo" />
            </div>
        </div>
    );
};

export default EntryView;
