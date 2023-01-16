import React, { useContext } from "react";
import "./SideBar.less";

import FirstpassLogo from "assets/svg/logo_small.svg";
import AppContext from "contexts/App.context";

import {
    DnsRounded,
    LogoutRounded,
    SearchRounded,
    SettingsRounded,
    ListAltRounded,
    AddBoxRounded,
    DeleteRounded,
    EditRounded
} from "@mui/icons-material";
import backend from "backend";
import { useEffect } from "react";
import AddCategory from "./components/AddCategory";

const SideBar = ({ currentCategory, setCurrentCategory, setSettingsVisible, settingsVisible }) => {
    const { db, setDb } = useContext(AppContext);

    const [addCategoryVisible, setAddCategoryVisible] = React.useState(false);

    async function addCategory(name) {
        const category = await backend.call({
            type: "CREATE_CATEGORY",
            data: {
                name
            }
        });
        console.log(category);
        setAddCategoryVisible(false);
        setDb({
            ...db,
            categories: [...db.categories, { category: name, id: category.id }]
        });
    }

    async function deleteCategory() {

    }

    return (
        <div className="sidebar">
            <div className="sidebarHeader">
                <FirstpassLogo id="headerLogo" />
                <p>Firstpass</p>
            </div>

            <div className="toolbar">
                <span>Categories</span>
                <div className="toolbarButton edit" onClick={"/* TODO */"}>
                    <EditRounded />
                </div>
                <div className="toolbarButton delete" onClick={"/* TODO */"}>
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
                        className={`category ${currentCategory === i ? "active" : ""}`}
                        onClick={() => {
                            setCurrentCategory(i);
                            setSettingsVisible(false);
                        }}>
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
        </div>
    );
};

export default SideBar;
