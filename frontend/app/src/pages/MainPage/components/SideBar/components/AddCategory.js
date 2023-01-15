import React, { useEffect, useState } from "react";
import "./AddCategory.less";

import { ListAltRounded } from "@mui/icons-material";

const AddCategory = ({ onAdd }) => {

    const [newCategory, setNewCategory] = React.useState("");

    useEffect(() => {
        const clickListener = (e) => {
            if (!e.target.closest(".addCategory")) {
                setAddCategoryVisible(false);
            }
        };
        const keyListener = (e) => {
            if (e.key === "Escape") {
                setAddCategoryVisible(false);
            }
        };

        document.addEventListener("click", clickListener);
        document.addEventListener("keydown", keyListener);

        return () => {
            document.removeEventListener("click", clickListener);
            document.removeEventListener("keydown", keyListener);
        };
    }, []);
    
    return (
        <div class="addCategory">
            <ListAltRounded />
            <input
                type="text"
                placeholder="Category Name"
                spellcheck="false"
                autoComplete="false"
                onInput={(e) => setNewCategory(e.target.value)}
                value={newCategory}
                autoFocus
            />
            <button onClick={() => onAdd(newCategory)}>Add</button>
        </div>
    );
};

export default AddCategory;
