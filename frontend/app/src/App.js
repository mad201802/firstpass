import React, { useState } from "react";

import "./App.less";

import MainPage from "./pages/MainPage/MainPage";
import LoginPage from "./pages/LoginPage/LoginPage";

const App = () => {
    const [db, setDb] = useState(null);

    return db ? <MainPage db={db} /> : <LoginPage setDb={setDb} />;
};

export default App;
