import React from "react"
import "./LoginPage.less"

import FirstpassLogo from "../../../assets/svg/logo_full.svg"
import WavesSvg from "../../../assets/svg/waves.svg"
import FormInput from "../../components/FormInput/FormInput"

import { KeyRounded } from "@mui/icons-material"
import Button from "../../components/Button/Button"

import backend from "../../backend"


const LoginPage = ({ setDb }) => {
    

    async function login() {
        const masterpassword = document.querySelector(".formInput input").value;
    
        try {
            const db = await backend.call({
                type: "OPEN_DB",
                masterpassword,
                filename: "C:\\Users\\test\\Documents\\test.fp",
            });
            setDB(db);
    
        } catch (e) {
            console.log(e);
            setDb({
                categories: ["test", "1234", "wow"],
            });
        }
    
    }

    
    return (
        <div className="loginPage">
            <div className="titleBar">Title Bar</div>
            <div className="loginForm-wrapper">
                <div className="loginForm">
                    <FirstpassLogo className="firstpassLogo" />

                    <FormInput
                        placeholder="Enter Masterpassword"
                        type="password"
                        iconLeft={<KeyRounded />}
                    />
                    <FormInput
                        placeholder="Enter Masterpassword"
                        type="password"
                        iconLeft={<KeyRounded />}
                    />

                    <Button onClick={login}>Login</Button>
                </div>
            </div>
            <WavesSvg />
        </div>
    );
};

export default LoginPage