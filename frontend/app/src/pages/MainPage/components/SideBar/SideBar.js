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
    AddBoxRounded
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
            categories: [...db.categories, category]
        });
    }

    return (
        <div className="sidebar">
            <div className="sidebarHeader">
                <FirstpassLogo id="headerLogo" />
                <p>Firstpass</p>
                <div class="addCategoryButton" onClick={() => setAddCategoryVisible(true)}>
                    <AddBoxRounded />
                </div>
            </div>

            <div className="categories">

                {addCategoryVisible && <AddCategory onAdd={addCategory} />}

                {db.categories?.map((category, i) => (
                    <div
                        key={i}
                        className={`category ${
                            currentCategory === i ? "active" : ""
                        }`}
                        onClick={() => {
                            setCurrentCategory(i);
                            setSettingsVisible(false);
                        }}
                    >
                        <ListAltRounded />
                        <p>{category.category}</p>
                    </div>
                ))}
            </div>

            <div
                className="settings"
                data-active={settingsVisible}
                onClick={() => setSettingsVisible(true)}
            >
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
                            data: {}
                        });
                    }}
                >
                    <LogoutRounded />
                </div>
            </div>
        </div>
    );
};

export default SideBar;
