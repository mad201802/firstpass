import React, { useContext } from "react";
import "./SideBar.less";

import FirstpassLogo from "assets/svg/logo_small.svg";
import AppContext from "contexts/App.context";

import {
    DnsRounded,
    LogoutRounded,
    SettingsRounded,
    ListAltRounded,
    AddBoxRounded,
    DeleteRounded,
    EditRounded
} from "@mui/icons-material";
import backend from "backend";
import AddCategory from "./components/AddCategory";
import { Popup } from "components";
import useShortcut from "hooks/useShortcut";

const SideBar = ({ currentCategory, setCurrentCategory, setSettingsVisible, settingsVisible }) => {
    const { db, setDb } = useContext(AppContext);

    const [addCategoryVisible, setAddCategoryVisible] = React.useState(false);
    const [deleteConfirmationVisible, setDeleteConfirmationVisible] = React.useState(false);

    async function addCategory(name) {
        try {
            const {data: category} = await backend.call({
                type: "CREATE_CATEGORY",
                data: {
                    name
                }
            });
            setAddCategoryVisible(false);
            setDb({
                ...db,
                categories: [...db.categories, { category: name, id: category.id }]
            });
            setCurrentCategory(category.id);

        } catch (e) {
            console.error(e);
        }
    }

    async function deleteCategory(confirmed=false) {
        if (!confirmed) {
            setDeleteConfirmationVisible(true);
            return;
        }

        setDeleteConfirmationVisible(false);
            
        try {
            const res = await backend.call({
                type: "DELETE_CATEGORY",
                data: {
                    id: currentCategory
                }
            });
            const index = db.categories.findIndex(c => c.id === currentCategory);
            const categories = db.categories.slice(0, index).concat(db.categories.slice(index + 1));
            const entries = db.entries.map(e => {
                if (e.category === currentCategory) {
                    e.category = 1;
                }
                return e;
            });
            setDb({
                ...db,
                categories,
                entries
            });

            setCurrentCategory(categories[index-1].id);

        } catch (e) {
            console.error(e);
        }
    }

    useShortcut(
        "Ctrl-Shift-N",
        () => setAddCategoryVisible(true)
    );
    useShortcut("Delete", () => deleteCategory());

    return (
        <div className="sidebar">
            <div className="sidebarHeader">
                <FirstpassLogo id="headerLogo" />
                <p>Firstpass</p>
            </div>

            <div className="toolbar">
                <span>Categories</span>
                <div className="toolbarButton edit" onClick={() => {}}>
                    <EditRounded />
                </div>
                <div className="toolbarButton delete" onClick={() => deleteCategory()}>
                    <DeleteRounded />
                </div>
                <div className="toolbarButton add" onClick={() => setAddCategoryVisible(true)}>
                    <AddBoxRounded />
                </div>
            </div>

            <div className="categories">
                {addCategoryVisible && (
                    <AddCategory onAdd={addCategory} onCancel={() => setAddCategoryVisible(false)} />
                )}

                {db.categories?.map((category, i) => (
                    <div
                        key={i}
                        className={`category ${currentCategory === category.id ? "active" : ""}`}
                        onClick={() => {
                            setCurrentCategory(category.id);
                            setSettingsVisible(false);
                        }}
                        onDragOver={e => {
                            e.preventDefault();
                            e.dataTransfer.dropEffect = "move";
                        }}
                        onDrop={async e => {
                            e.preventDefault();
                            const data = e.dataTransfer.getData("text");
                            const entry = db.entries.find(e => e.id == data);
                            if (entry && entry.category !== category.id) {
                                try {
                                    await backend.call({
                                        type: "UPDATE_ENTRY",
                                        data: {
                                            id: entry.id,
                                            category_id: category.id
                                        }
                                    });
                                    setDb({
                                        ...db,
                                        entries: db.entries.map(e => {
                                            if (e.id == entry.id) {
                                                e.category = category.id;
                                            }
                                            return e;
                                        })
                                    });
                                } catch (e) {
                                    console.error(e);
                                }
                            }
                        }}
                    >
                        <ListAltRounded />
                        <p>{category.category}</p>
                    </div>
                ))}
            </div>

            <div className="settings" data-active={settingsVisible} onClick={() => setSettingsVisible(true)}>
                <SettingsRounded />
                <p>Settings</p>
            </div>
            <div className="currentDBInfo">
                <DnsRounded />
                <p>DB Name</p>
                <div
                    className="logoutButton"
                    onClick={() => {
                        setDb();
                        backend.call({
                            type: "CLOSE_DB",
                            data: {},
                        });
                    }}>
                    <LogoutRounded />
                </div>
            </div>
            { deleteConfirmationVisible && <Popup
                onClose={() => setDeleteConfirmationVisible(false)}
                onSubmit={() => deleteCategory(true)}
                title="Confirm Action"
            >
                {`Are you sure you want to delete category "${
                    db.categories.find(c => c.id == currentCategory)?.category
                }"? This action cannot be undone.`}
            </Popup>}
        </div>
    );
};

export default SideBar;
