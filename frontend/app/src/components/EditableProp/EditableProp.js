import React, { useEffect, useState } from "react";
import "./EditableProp.less";
import { Button, FormInput, Tooltip } from "components";
import useShortcut from "hooks/useShortcut";
import {
    CheckRounded,
    CopyAllRounded,
    EditRounded,
    SaveRounded,
    VisibilityRounded,
    VisibilityOffRounded,
} from "@mui/icons-material";
import backend from "backend";
import { tooltipClasses } from "@mui/material";

const EditableProp = ({
    icon,
    name,
    placeholder,
    password,
    onUpdate,
    value,
    multiline,
    title,
    copyable = false,
    url = false,
}) => {
    const [editable, setEditable] = useState(false);
    const [copied, setCopied] = useState(false);
    const [hidden, setHidden] = useState(!!password);
    const inputRef = React.useRef(null);
    const [isHover, setIsHover] = useState(false);

    useShortcut("Tab", () => setEditable(false), editable);
    useShortcut(
        multiline ? "Ctrl-Enter" : "Enter",
        () => {
            setEditable(false);
        },
        editable
    );

    useEffect(() => {
        if (editable) {
            inputRef.current.focus();
        }
    }, [editable]);

    return (
        <div
            className="editableProp"
            data-editable={editable}
            data-multiline={multiline}
            data-title={title}
            data-nodivider={password}
            onDoubleClick={e => {
                if (url && (e.target.tagName === "INPUT" || e.target.tagName === "TEXTAREA")) return;
                setEditable(true);
            }}
            onClick={
                url && !editable
                    ? e => {
                          if (e.target.tagName !== "INPUT" && e.target.tagName !== "TEXTAREA") return;
                          backend.openURL(value);
                      }
                    : null
            }>
            {icon}
            {!multiline ? (
                <FormInput
                    ref={inputRef}
                    name={name}
                    placeholder={placeholder || name.replace(/^\w/, c => c.toUpperCase())}
                    type={hidden ? "password" : null}
                    value={value}
                    onInput={onUpdate}
                    disabled={!editable}
                    spellCheck={false}
                    data-url={url && !editable}
                    inputProps={{ onBlur: () => setEditable(false) }}
                />
            ) : (
                <textarea
                    ref={inputRef}
                    name={name}
                    placeholder={placeholder || name.replace(/^\w/, c => c.toUpperCase())}
                    value={value}
                    onInput={onUpdate}
                    disabled={!editable}
                    spellCheck={false}
                    onBlur={() => setEditable(false)}
                />
            )}
            <div className="buttonGroup">
                {password && (
                    <Button onClick={() => setHidden(v => !v)}>
                        {hidden ? <VisibilityRounded /> : <VisibilityOffRounded style={{ fill: "var(--error)" }} />}
                    </Button>
                )}
                <div onMouseEnter={() => setIsHover(true)} onMouseLeave={() => setIsHover(false)}>
                    <Button className="editButton" onClick={() => setEditable(!editable)}>
                        {editable ? <SaveRounded /> : <EditRounded />}
                        {isHover ? editable? <span className="buttonTooltip">Save</span>:<span className="buttonTooltip">Edit</span>: ""}
                    </Button>
                </div>
                <div onMouseEnter={() => setIsHover(true)} onMouseLeave={() => setIsHover(false)}></div>
                {copyable && (
                    <Button
                        title="Copy to clipboard"
                        onClick={() => {
                            navigator.clipboard.writeText(value);
                            setCopied(v => {
                                if (!v) setTimeout(() => setCopied(false), 2000);
                                return true;
                            });
                        }}>
                        {copied ? <CheckRounded style={{ fill: "var(--success)" }} /> : <CopyAllRounded />}
                    </Button>
                )}
            </div>
        </div>
    );
};

export default EditableProp;
