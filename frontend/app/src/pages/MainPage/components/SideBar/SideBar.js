import React, { useContext, useEffect, useState } from "react";
import "./SideBar.less";

import FirstpassLogo from "assets/svg/logo_small.svg";
import AppContext from "contexts/App.context";
import EggAudio from "assets/audio/egg.mp3";

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
import Category from "./components/Category";

const SideBar = ({ currentCategory, setCurrentCategory, setSettingsVisible, settingsVisible, setCurrentEntry }) => {
    const { db, setDb, settings: { editCategoryShortcut, createCategoryShortcut }, setRainbowMode } = useContext(AppContext);

    const [addCategoryVisible, setAddCategoryVisible] = useState(false);
    const [deleteConfirmationVisible, setDeleteConfirmationVisible] = useState(false);
    const [logoutConfirmationVisible, setLogoutConfirmationVisible] = useState(false);
    const [editCategoryVisible, setEditCategoryVisible] = useState(false);


    const [clickCount, setClickCount] = useState(0);
    useEffect(() => {
        if (clickCount !== 10) return;
        new Audio(EggAudio).play();
        setRainbowMode(true);
        setClickCount(0);
    }, [clickCount]);

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
        if (currentCategory === 1) return;
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
    function editCategory() {
        if (currentCategory === 1) return;
        setEditCategoryVisible(true);
    }

    function logout(confirm=false) {
        if (!confirm) {
            setLogoutConfirmationVisible(true);
            return;
        }
        setLogoutConfirmationVisible(false);
        setDb();
        backend.call({
            type: "CLOSE_DB",
            data: {},
        });
    }

    useShortcut(
        createCategoryShortcut,
        () => setAddCategoryVisible(true)
    );
    useShortcut("Delete", () => deleteCategory());
    useShortcut(editCategoryShortcut, editCategory);

    return (
        <div className="sidebar">
            <div className="sidebarHeader" onClick={() => setClickCount(c => c+1)}>
                <FirstpassLogo id="headerLogo" />
                <p>Firstpass</p>
            </div>

            <div className="toolbar">
                <span>Categories</span>
                <div className="toolbarButton edit" onClick={editCategory}>
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

                {db.categories?.map(category => (
                    <Category
                        key={category.id}
                        {...category}
                        currentCategory={currentCategory}
                        setCurrentCategory={setCurrentCategory}
                        setSettingsVisible={setSettingsVisible}
                        editCategoryVisible={editCategoryVisible}
                        setEditCategoryVisible={setEditCategoryVisible}
                        setCurrentEntry={setCurrentEntry}
                    />
                ))}
            </div>

            <div className="settings" data-active={settingsVisible} onClick={() => {
                    setSettingsVisible(v => !v);
                    setCurrentEntry(null);
                }}>
                <SettingsRounded />
                <p>Settings</p>
            </div>
            <div className="currentDBInfo">
                <DnsRounded />
                <p>{db.name}</p>
                <div
                    className="logoutButton"
                    onClick={() => logout()}>
                    <LogoutRounded />
                </div>
            </div>
            {deleteConfirmationVisible && (
                <Popup
                    size="small"
                    type="danger"
                    submitText="Delete"
                    onClose={() => setDeleteConfirmationVisible(false)}
                    onSubmit={() => deleteCategory(true)}
                    title="Delete Category">
                    {`Are you sure you want to delete "${
                        db.categories.find(c => c.id == currentCategory)?.category
                    }"?`}
                    <br />
                    This action cannot be undone.
                </Popup>
            )}
            {logoutConfirmationVisible && (
                <Popup
                    size="small"
                    type="danger"
                    submitText="Logout"
                    onClose={() => setLogoutConfirmationVisible(false)}
                    onSubmit={() => logout(true)}
                    title="Logout"
                >
                    Are you sure you want to logout?
                </Popup>
            )}
        </div>
    );
};

export default SideBar;
