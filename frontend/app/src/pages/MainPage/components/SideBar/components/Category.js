import React, { useContext, useEffect, useRef, useState } from "react";
import "./Category.less";
import useShortcut from "hooks/useShortcut";
import backend from "backend";

import { KeyboardDoubleArrowRightRounded, ListAltRounded } from "@mui/icons-material";
import AppContext from "contexts/App.context";

const Category = ({ currentCategory, category, id, setCurrentCategory, setSettingsVisible, editCategoryVisible, setEditCategoryVisible, setCurrentEntry }) => {

    const { db, setDb } = useContext(AppContext);

    const [value, setValue] = useState(category);
    const [dragover, setDragover] = useState(false);
    const elRef = useRef(null);
    const active = currentCategory === id;

    useEffect(() => {
        if (active && editCategoryVisible) {
            elRef.current.querySelector("input").select();
        }
    }, [active, editCategoryVisible]);

    async function update() {
        if (value === category) {
            setEditCategoryVisible(false);
            return;
        };
        try {
            await backend.call({
                type: "UPDATE_CATEGORY",
                data: {
                    id,
                    new_name: value,
                }
            });
            setDb({
                ...db,
                categories: db.categories.map(c => {
                    if (c.id === id) {
                        c.category = value;
                    }
                    return c;
                })
            });
            setEditCategoryVisible(false);
        } catch (e) {
            console.error(e);
            setValue(category);
        }
        setEditCategoryVisible(false);
    }

    useShortcut("Enter", update, active);
    useShortcut("Escape", () => {
        setEditCategoryVisible(false);
        setValue(category);
    }, active);

    async function onDrop(e) {
        e.preventDefault();
        setDragover(false);

        const data = e.dataTransfer.getData("entry.id");
        const entry = db.entries.find(e => e.id == data);

        if (entry && entry.category !== id) {
            try {
                await backend.call({
                    type: "UPDATE_ENTRY",
                    data: {
                        id: entry.id,
                        category: id,
                    },
                });
                setDb({
                    ...db,
                    entries: db.entries.map(e => {
                        if (e.id == entry.id) {
                            e.category = id;
                        }
                        return e;
                    }),
                });
            } catch (e) {
                console.error(e);
            }
        }
    }

    function onDragOver(e) {
        e.preventDefault();
        // only allow drop if dataTransfer has entry.id
        if (e.dataTransfer.types.includes("entry.id")) {
            e.dataTransfer.dropEffect = "move";
        } else {
            e.dataTransfer.dropEffect = "none";
        }
    }

    function onDragEnter(e) {
        e.preventDefault();
        setDragover(true);
    }
    function onDragLeave(e) {
        e.preventDefault();
        setDragover(false);
    }

    return (
        <div
            className="category"
            data-dragover={dragover}
            data-active={active}
            onClick={() => {
                setCurrentCategory(id);
                setCurrentEntry(null);
                setSettingsVisible(false);
            }}
            onDragOver={onDragOver}
            onDrop={onDrop}
            onDragEnter={onDragEnter}
            onDragLeave={onDragLeave}
            ref={elRef}
        >
            {dragover ? <KeyboardDoubleArrowRightRounded /> : <ListAltRounded />}
            {editCategoryVisible && active ? (
                <input spellCheck={false} autoFocus={true} type="text" value={value} onInput={e => setValue(e.target.value)} onBlur={update} />
            ) : (
                <p>{category}</p>
            )}
        </div>
    );
};

export default Category;
