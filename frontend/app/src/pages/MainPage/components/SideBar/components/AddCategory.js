import React, { useEffect, useState } from "react";
import "./AddCategory.less";

import { ListAltRounded } from "@mui/icons-material";

const AddCategory = ({ onAdd, onCancel }) => {

    const [newCategory, setNewCategory] = React.useState("");

    function add() {
        if(newCategory.length >= 3) onAdd(newCategory);
    }

    useEffect(() => {
        const clickListener = (e) => {
            if (!e.target.closest(".addCategory") && !e.target.closest(".toolbarButton.add")) {
                onCancel();
            }
        };
        const keyListener = (e) => {
            if (e.key === "Escape") onCancel();
            else if (e.key === "Enter") add();
        };

        document.addEventListener("click", clickListener);
        document.addEventListener("keydown", keyListener);

        return () => {
            document.removeEventListener("click", clickListener);
            document.removeEventListener("keydown", keyListener);
        };
    }, []);
    
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
