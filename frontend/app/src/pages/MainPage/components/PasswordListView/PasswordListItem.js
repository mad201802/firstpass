import React, { useContext, useRef } from "react";
import "./PasswordListItem.less";
import { UrlLogo } from "components";
import AppContext from "contexts/App.context";

const PasswordListItem = ({ entry, visible, setCurrentEntry }) => {

    const { settings } = useContext(AppContext);

    const logoRef = useRef();

    return (
        <div
            className="passwordListItem"
            data-compact={settings.compactView}
            data-hidden={!visible}
            draggable={true}
            onDragStart={e => {
                e.dataTransfer.setData("text/plain", entry.name);
                e.dataTransfer.setData("entry.id", entry.id);
                e.dataTransfer.setDragImage(logoRef.current, -12, -12);
                e.dropEffect = "move";
            }}
            onClick={() => setCurrentEntry(entry)}
        >
            <UrlLogo className="passwordListItem-logo" entry={entry} ref={logoRef}/>
            <div className="passwordListItem-info1">
                <div className="passwordListItem-name">{entry.name || "Unnamed Entry"}</div>
                {entry.url && settings.showSensitiveInfo && (
                    <div className="passwordListItem-infoURL">{entry.url.replace(/.+\/\/|www\.|\/.+$/gi, "")}</div>
                )}
            </div>
            {settings.showSensitiveInfo && (
                <div className="passwordListItem-info2">
                    <div className="passwordListItem-infoUsername">{entry.username}</div>
                    <div className="passwordListItem-infoPassword">{entry.password}</div>
                </div>
            )}
        </div>
    );
};

export default PasswordListItem;
