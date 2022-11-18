import React from "react";
import "./LoginPage.less";

const LoginPage = ({ setDb }) => {
    function login() {
        setDb({ categories: ["test", "1234", "wow"] });
    }

    return (
        <div>
            <h2>LoginPage</h2>
            <button onClick={login}>Login</button>
        </div>
    );
};

export default LoginPage;
