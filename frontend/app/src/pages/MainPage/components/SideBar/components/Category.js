import React, { useEffect, useRef, useState } from "react";
import "./Category.less";
import useShortcut from "hooks/useShortcut";
import backend from "backend";

import { ListAltRounded } from "@mui/icons-material";

const Category = ({ currentCategory, category, id, setCurrentCategory, setSettingsVisible, editCategoryVisible, setEditCategoryVisible }) => {

    const [value, setValue] = useState(category);
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
                    name: value,
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

        const data = e.dataTransfer.getData("text");
        const entry = db.entries.find(e => e.id == data);

        if (entry && entry.category !== category.id) {
            try {
                await backend.call({
                    type: "UPDATE_ENTRY",
                    data: {
                        id: entry.id,
                        category: category.id,
                    },
                });
                setDb({
                    ...db,
                    entries: db.entries.map(e => {
                        if (e.id == entry.id) {
                            e.category = category.id;
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
        e.dataTransfer.dropEffect = "move";
    }

    function onDragEnter(e) {
        e.preventDefault();
        elRef.current.classList.add("dragover");
    }
    function onDragLeave(e) {
        e.preventDefault();
        elRef.current.classList.remove("dragover");
    }

    return (
        <div
            className="category"
            data-active={active}
            onClick={() => {
                setCurrentCategory(id);
                setSettingsVisible(false);
            }}
            onDragOver={onDragOver}
            onDrop={onDrop}
            onDragEnter={onDragEnter}
            onDragLeave={onDragLeave}
            ref={elRef}
        >
            <ListAltRounded />
            {editCategoryVisible && active ? (
                <input spellCheck={true} autoFocus={true} type="text" value={value} onInput={e => setValue(e.target.value)} onBlur={update} />
            ) : (
                <p>{category}</p>
            )}
        </div>
    );
};

export default Category;
