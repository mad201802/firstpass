import React, { useEffect, useState } from "react";

import "./App.less";

import MainPage from "./pages/MainPage/MainPage";
import LoginPage from "./pages/LoginPage/LoginPage";

import backend from "./backend";
window.backend = backend;


const App = () => {
    const [db, setDb] = useState(null);

    useEffect(() => {
        const cb = data => console.log("Error", data);
        backend.onError(cb);
    
        return () => {
            backend.onError(null);
        }
    }, [])
    

    return db ? <MainPage db={db} /> : <LoginPage setDb={setDb} />;
};

export default App;
