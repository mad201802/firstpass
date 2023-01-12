import React, { useEffect, useState } from "react";

import "./App.less";

import MainPage from "pages/MainPage/MainPage";
import LoginPage from "pages/LoginPage/LoginPage";
import CreatePage from "pages/CreatePage/CreatePage";

import backend from "backend";

import AppContext from "contexts/App.context";

const App = () => {
    const [db, setDb] = useState();
    const [login, setLogin] = useState(true);

    useEffect(() => {
        const cb = (data) => console.log("Error", data);
        backend.onError(cb);

        return () => {
            backend.onError(null);
        };
    }, []);

    useEffect(() => {
        // load db from localstorage
        const db = localStorage.getItem("db");
        if (db) {
            setDb(JSON.parse(db));
        }
    })

    useEffect(() => {
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
                setLogin
            }}
        >
            {db ? <MainPage /> : login ? <LoginPage /> : <CreatePage />}
        </AppContext.Provider>
    );
};

export default App;
