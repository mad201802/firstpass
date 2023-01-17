import React from "react";
import "./PasswordListItem.less";

function getDomain(url) {
    return url
        .replace(/https?:\/\//i, "")
        .split("/")[0]
        .split(".")
        .slice(-2, -1)[0];
}

const PasswordListItem = ({ entry, visible }) => {
    const [imgLoadError, setImgLoadError] = React.useState(false);

    return (
        <div className="passwordListItem" data-hidden={!visible}>
            <div className="passwordListItem-logo">
                {imgLoadError ? (
                    <span>{entry.url ? getDomain(entry.url)[0] : entry.name[0]}</span>
                ) : (
                    <img src={entry.url + "/favicon.ico"} alt="favicon" onError={() => setImgLoadError(true)} />
                )}
            </div>
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
