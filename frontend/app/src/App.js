import React, { useEffect, useState } from "react";

import "./App.less";

import MainPage from "pages/MainPage/MainPage";
import LoginPage from "pages/LoginPage/LoginPage";
import CreatePage from "pages/CreatePage/CreatePage";

import backend from "backend";

import AppContext from "contexts/App.context";


const App = () => {
    const [db, setDb] = useState();
    const [settings, setSettings] = useState({
        theme: {
            primary: "#f5b302",
            primaryLight: "#fed053",
            primaryDark: "#f5a303",

            bg: "#1f232a",
            bgLight: "#2b2f36", 
            bgLighter: "#3b3f46",

            divider: "#3b3f46",

            text: "#f5f5f5",
            textDark: "#b3b3b3",

            radius: "5px",
            radiusLg: "10px",
        },

        createCategoryShortcut: "Ctrl+Shift+N",
        createEntryShortcut: "Ctrl+N",
        editCategoryShortcut: "F2",
        searchShortcut: "Ctrl+F",

        loadFavicons: true,

        ...JSON.parse(localStorage.getItem("settings") || "{}")
    });

    // Save settings to localstorage
    useEffect(() => {
        localStorage.setItem("settings", JSON.stringify(settings));
    }, [settings]);

    // Apply theme
    useEffect(() => {
        for (const [key, value] of Object.entries(settings.theme)) {
            let pkey = key.replace(/([A-Z])/g, "-$1").toLowerCase();
            document.body.style.setProperty(`--${pkey}`, value);
        }
    }, [settings.theme]);


    const [login, setLogin] = useState(true);

    useEffect(() => {
        const cb = (data) => console.log("Error", data);
        backend.onError(cb);

        return () => {
            backend.onError(null);
        };
    }, []);

    useEffect(() => {
        if (window.isPackaged) return;
        // load db from localstorage
        const db = localStorage.getItem("db");
        if (db) {
            setDb(JSON.parse(db));
        }
    }, []);

    useEffect(() => {
        if (window.isPackaged) return;
        // save db to localstorage
        if (db) {
            localStorage.setItem("db", JSON.stringify(db));
        } else {
            localStorage.removeItem("db");
        }
    }, [db]);

    return (
        <AppContext.Provider
            value={{
                db,
                setDb,
                setLogin,
                settings,
                setSettings,
            }}
        >
            {db ? <MainPage /> : login ? <LoginPage /> : <CreatePage />}
        </AppContext.Provider>
    );
};

export default App;
