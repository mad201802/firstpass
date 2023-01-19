import React, { useState } from "react";
import "./MainPage.less";

import SideBar from "./components/SideBar/SideBar";
import { TitleBar } from "components";
import PasswordListView from "./components/PasswordListView/PasswordListView";

import AddEntryPopup from "./components/AddEntryPopup/AddEntryPopup";
import SettingsView from "./components/SettingsView/SettingsView";
import EntryView from "./components/EntryView/EntryView";
import useShortcut from "hooks/useShortcut";

const MainPage = () => {
    const [currentCategory, setCurrentCategory] = useState(1);
    const [addEntryPopupVisible, setAddEntryPopupVisible] = useState(false);

    const [settingsVisible, setSettingsVisible] = useState(false);
    const [currentEntry, setCurrentEntry] = useState(null);

    return (
        <div className="mainPage">
            <SideBar
                currentCategory={currentCategory}
                setCurrentCategory={setCurrentCategory}
                setSettingsVisible={setSettingsVisible}
                settingsVisible={settingsVisible}
                setCurrentEntry={setCurrentEntry}
            />

            <div className="mainPageContent">
                <TitleBar />
                {settingsVisible ? (
                    <SettingsView setSettingsVisible={setSettingsVisible} />
                ) : currentEntry ? (
                    <EntryView entry={currentEntry} setCurrentEntry={setCurrentEntry} />
                ) : (
                    <PasswordListView
                        currentCategory={currentCategory}
                        setAddEntryPopupVisible={setAddEntryPopupVisible}
                        setCurrentEntry={setCurrentEntry}
                    />
                )}
            </div>
            {addEntryPopupVisible && (
                <AddEntryPopup setAddEntryPopupVisible={setAddEntryPopupVisible} currentCategory={currentCategory} />
            )}
        </div>
    );
};

export default MainPage;
