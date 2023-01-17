import React from "react";

const useShortcut = (shortcut, callback) => {
    
    const key = shortcut.split(/[\+-]/g).pop().toLowerCase();
    const shift = shortcut.toLowerCase().includes("shift");
    const ctrl = shortcut.toLowerCase().includes("ctrl");
    const alt = shortcut.toLowerCase().includes("alt");

    const handler = e => {
        if (e.key.toLowerCase() === key && e.shiftKey === shift && e.ctrlKey === ctrl && e.altKey === alt) {
            callback();
        }
    }

    React.useEffect(() => {
        document.addEventListener("keydown", handler);
        return () => document.removeEventListener("keydown", handler);
    }, [shortcut, callback]);
}

export default useShortcut;