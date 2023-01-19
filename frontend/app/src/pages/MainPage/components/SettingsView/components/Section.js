import React, { useState } from "react";
import "./Section.less";
import { AddRounded, RemoveRounded } from "@mui/icons-material";

const Section = ({ children, title, open: defaultOpened, icon }) => {

    const [open, setOpen] = useState(!!defaultOpened);

    return (
        <div className="settingsSection" data-open={open}>
            <div className="settingsSection-header" onClick={() => setOpen(!open)}>
                {icon}
                <p>{title}</p>
                {open ? <RemoveRounded className="foldButton" /> : <AddRounded className="foldButton" />}
            </div>

            <div className="settingsSection-content">
                {children}
            </div>
        </div>
    );
};

export default Section;
