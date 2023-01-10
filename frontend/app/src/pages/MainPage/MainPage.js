import React, { useState, useContext } from "react";
import "./MainPage.less";

import SideBar from "./components/SideBar/SideBar";
import { TitleBar } from "components";
import PasswordListView from "./components/PasswordListView/PasswordListView";

import AppContext from "contexts/App.context";
import AddEntryPopup from "./components/AddEntryPopup/AddEntryPopup";

const MainPage = () => {
    const [currentCategory, setCurrentCategory] = useState(0);
    const [addEntryPopupVisible, setAddEntryPopupVisible] = useState(false);

    return (
      <div className="mainPage">
        <SideBar
          currentCategory={currentCategory}
          setCurrentCategory={setCurrentCategory}
        />

        <div className="mainPageContent">
          <TitleBar />
          <PasswordListView
            currentCategory={currentCategory}
            setAddEntryPopupVisible={setAddEntryPopupVisible}
          />
        </div>
        {addEntryPopupVisible && <AddEntryPopup setAddEntryPopupVisible={setAddEntryPopupVisible} />}
      </div>
    );
};

export default MainPage;
