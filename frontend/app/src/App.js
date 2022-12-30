import React, { useEffect, useState } from "react";

import "./App.less";

import MainPage from "./pages/MainPage/MainPage";
import LoginPage from "./pages/LoginPage/LoginPage";
import CreatePage from "./pages/CreatePage/CreatePage";

import backend from "./backend";
window.backend = backend;


const App = () => {
    const [db, setDb] = useState();
    const [login, setLogin] = useState(true);

    useEffect(() => {
        const cb = data => console.log("Error", data);
        backend.onError(cb);
    
        return () => {
            backend.onError(null);
        }
    }, [])
    
    if (db) return <MainPage db={db} setDb={setDb} />;
    return login ? <LoginPage setDb={setDb} setLogin={setLogin} /> : <CreatePage setDb={setDb} setLogin={setLogin} />;
};

export default App;
