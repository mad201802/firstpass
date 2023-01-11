import React, { useState, useContext } from "react";
import "./MainPage.less";

import SideBar from "./components/SideBar/SideBar";
import { TitleBar } from "components";
import PasswordListView from "./components/PasswordListView/PasswordListView";

import AppContext from "contexts/App.context";
import AddEntryPopup from "./components/AddEntryPopup/AddEntryPopup";
import SettingsView from "./components/SettingsView/SettingsView";

const MainPage = () => {
    const [currentCategory, setCurrentCategory] = useState(0);
    const [addEntryPopupVisible, setAddEntryPopupVisible] = useState(false);

    const [settingsVisible, setSettingsVisible] = useState(false);

    return (
      <div className="mainPage">
        <SideBar
          currentCategory={currentCategory}
          setCurrentCategory={setCurrentCategory}
          setSettingsVisible={setSettingsVisible}
          settingsVisible={settingsVisible}
        />

        <div className="mainPageContent">
          <TitleBar />
          {!settingsVisible ? (
            <PasswordListView
              currentCategory={currentCategory}
              setAddEntryPopupVisible={setAddEntryPopupVisible}
            />
          ) : (
            <SettingsView setSettingsVisible={setSettingsVisible} />
          )}
        </div>
        {addEntryPopupVisible && <AddEntryPopup setAddEntryPopupVisible={setAddEntryPopupVisible} />}
      </div>
    );
};

export default MainPage;
