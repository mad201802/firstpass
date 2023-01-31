import React, { useEffect, useState } from "react";

import "./App.less";

import MainPage from "pages/MainPage/MainPage";
import LoginPage from "pages/LoginPage/LoginPage";
import CreatePage from "pages/CreatePage/CreatePage";
import FatalErrorPage from "pages/FatalErrorPage/FatalErrorPage";

import backend from "backend";

import AppContext from "contexts/App.context";
import { AnimatedPatternBG } from "components";


const App = () => {
    // The database loaded from the backend after login
    const [db, setDb] = useState();

    // Application-wide settings (stored in localstorage)
    const [settings, setSettings] = useState({
        theme: "*default",

        createCategoryShortcut: "Ctrl+Shift+N",
        createEntryShortcut: "Ctrl+N",
        editCategoryShortcut: "F2",
        searchShortcut: "Ctrl+F",

        loadFavicons: true,
        compactView: false,
        showSensitiveInfo: true,

        ...JSON.parse(localStorage.getItem("settings") || "{}")
    });

    // The current theme colors
    const [theme, setTheme] = useState({
            
    });

    const [fatalError, setFatalError] = useState(false);
    const [login, setLogin] = useState(true);


    // Save settings to localstorage
    useEffect(() => {
        localStorage.setItem("settings", JSON.stringify(settings));
    }, [settings]);



    // ########################################
    // Theme Management
    // ########################################
    useEffect(() => {
        async function loadTheme() {
            const t = await backend.getTheme(settings.theme);
            console.log(`Loaded theme "${settings.theme}"`, t);
            setTheme({ ...theme, ...t });
        }
        if (settings.theme) loadTheme();
    }, [settings.theme]);

    // Apply theme color to body
    useEffect(() => {
        for (const [key, value] of Object.entries(theme)) {
            let pkey = key.replace(/([A-Z])/g, "-$1").toLowerCase();
            document.body.style.setProperty(`--${pkey}`, value);
        }
    }, [theme]);



    // ########################################
    // Rainbow Mode
    // ########################################
    const [rainbowMode, setRainbowMode] = useState(false);
    useEffect(() => {
        if (rainbowMode) {
            window.__hue = 0;
            window.rainbowInterval = setInterval(() => {
                const color = `hsl(${window.__hue}, 100%, 65%)`;
                const colorLight = `hsl(${window.__hue}, 80%, 75%)`;
                const colorDark = `hsl(${window.__hue}, 80%, 55%)`;
                document.body.style.setProperty(`--primary`, color);
                document.body.style.setProperty(`--primary-light`, colorLight);
                document.body.style.setProperty(`--primary-dark`, colorDark);
                window.__hue = (window.__hue + 2) % 360;
            }, 20);
        } else {
            clearInterval(window.rainbowInterval);
        }
    }, [rainbowMode])



    // ########################################
    // Fatal Error Handling
    // ########################################
    useEffect(() => {
        const cb = (data) => {
            setDb(null);
            if (data.fatal) setFatalError(data);
        };
        backend.onError(cb);

        return () => {
            backend.onError(null);
        };
    }, []);


    // ########################################
    // Store DB in localstorage (dev only)
    // ########################################
    useEffect(() => {
        if (window.isPackaged) return;
        const db = localStorage.getItem("db");
        if (db) {
            setDb(JSON.parse(db));
        }
    }, []);

    // save db to localstorage for development purposes
    useEffect(() => {
        if (window.isPackaged) return;
        if (db) {
            localStorage.setItem("db", JSON.stringify(db));
        } else {
            localStorage.removeItem("db");
        }
    }, [db]);



    if (fatalError) return <FatalErrorPage error={fatalError} />;

    return (
        <AppContext.Provider
            value={{
                settings, setSettings,
                theme, setTheme, setRainbowMode,
                db, setDb, setLogin,
            }}
        >
            {db ? (
                <MainPage />
            ) : (
                <>
                    {login ? <LoginPage /> : <CreatePage />}
                    <AnimatedPatternBG />
                </>
            )}
        </AppContext.Provider>
    );
};

export default App;
