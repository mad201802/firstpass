import React, { useEffect, useState } from "react";

import "./App.less";

import MainPage from "pages/MainPage/MainPage";
import LoginPage from "pages/LoginPage/LoginPage";
import CreatePage from "pages/CreatePage/CreatePage";

import backend from "backend";

import AppContext from "contexts/App.context";

const App = () => {
    // TODO: remove
    const [db, setDb] = useState({
        entries: [],
        categories: [
            {
                name: "test",
                id: 123
            }
        ],
    });
    const [login, setLogin] = useState(true);

    useEffect(() => {
        const cb = (data) => console.log("Error", data);
        backend.onError(cb);

        return () => {
            backend.onError(null);
        };
    }, []);

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
