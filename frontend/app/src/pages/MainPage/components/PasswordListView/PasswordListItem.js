import React, { useRef } from "react";
import "./PasswordListItem.less";
import { UrlLogo } from "components";

const PasswordListItem = ({ entry, visible, setCurrentEntry }) => {
    const [imgLoadError, setImgLoadError] = React.useState(!entry.url?.match(/https?:\/\/.*\..{2,}/i));
    const logoRef = useRef();

    return (
        <div
            className="passwordListItem"
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
                <div className="passwordListItem-name">{entry.name}</div>
                <div className="passwordListItem-infoURL">{entry.url}</div>
            </div>
            <div className="passwordListItem-info2">
                <div className="passwordListItem-infoUsername">{entry.username}</div>
                <div className="passwordListItem-infoPassword">{entry.password}</div>
            </div>
        </div>
    );
};

export default PasswordListItem;
