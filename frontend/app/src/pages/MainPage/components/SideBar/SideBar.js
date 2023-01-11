import React, { useContext } from "react";
import "./SideBar.less";

import FirstpassLogo from "assets/svg/logo_small.svg";
import AppContext from "contexts/App.context";

import {
    DnsRounded,
    LogoutRounded,
    SearchRounded,
    SettingsRounded,
    ShoppingBagRounded
} from "@mui/icons-material";
import backend from "backend";

const SideBar = ({ currentCategory, setCurrentCategory, setSettingsVisible, settingsVisible }) => {
    const { db, setDb } = useContext(AppContext);

    return (
        <div className="sidebar">
            <div className="sidebarHeader">
                <FirstpassLogo id="headerLogo" />
                <p>Firstpass</p>
                <SearchRounded style={{ transform: "scaleX(-1)" }} />
            </div>

            <div className="categories">
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
                        <ShoppingBagRounded />
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
                <div className="logoutButton" onClick={() => {
                    setDb();
                    backend.call({
                        type: "CLOSE_DB",
                        data: {}
                    });
                }}>
                    <LogoutRounded />
                </div>
            </div>
        </div>
    );
};

export default SideBar;
