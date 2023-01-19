import React from "react";
import "./AddCategory.less";

import { ListAltRounded } from "@mui/icons-material";
import useShortcut from "hooks/useShortcut";
import useEventListener from "hooks/useEventListener";

const AddCategory = ({ onAdd, onCancel }) => {

    const [newCategory, setNewCategory] = React.useState("");

    function add() {
        if(newCategory.length >= 3) onAdd(newCategory);
    }

    useEventListener("click", (e) => {
        if (!e.target.closest(".addCategory") && !e.target.closest(".toolbarButton.add")) {
            onCancel();
        }
    });

    useShortcut("Escape", () => onCancel());
    useShortcut("Enter", () => add());
    
    return (
        <div className="addCategory">
            <ListAltRounded />
            <input
                type="text"
                placeholder="Category Name"
                spellCheck="false"
                autoComplete="false"
                onInput={(e) => setNewCategory(e.target.value)}
                value={newCategory}
                autoFocus
            />
            <button onClick={add}>Add</button>
        </div>
    );
};

export default AddCategory;
