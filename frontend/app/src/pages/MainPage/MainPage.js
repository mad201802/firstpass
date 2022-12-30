import React, { useState, useContext } from "react";
import "./MainPage.less";

import SideBar from "./components/SideBar/SideBar";
import { TitleBar } from "components";
import PasswordListView from "./components/PasswordListView/PasswordListView";

import AppContext from "contexts/App.context";

const MainPage = () => {
    const [currentCategory, setCurrentCategory] = useState(0);

    return (
        <div className="mainPage">
            <SideBar
                currentCategory={currentCategory}
                setCurrentCategory={setCurrentCategory}
            />

            <div className="mainPageContent">
                <TitleBar />
                <PasswordListView currentCategory={currentCategory} />
            </div>
        </div>
    );
};

export default MainPage;
