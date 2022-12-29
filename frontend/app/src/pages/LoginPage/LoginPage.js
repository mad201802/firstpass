import React from "react"
import "./LoginPage.less"

import FirstpassLogo from "../../../assets/svg/logo_full.svg"
import WavesSvg from "../../../assets/svg/waves.svg"
import FormInput from "../../components/FormInput/FormInput"

import { KeyRounded, DnsRounded, AddRounded} from "@mui/icons-material"
import Button from "../../components/Button/Button"

import backend from "../../backend"
import DropdownMenu from "../../components/DropdownMenu/DropdownMenu"
import TitleBar from "../../components/TitleBar/TitleBar"


const recentDBs = [
    {
        name: "Firstpass Default Database",
        date: "2021-10-10",
        filename: "C:\\Users\\test\\Documents\\test.fp",
    },
    {
        name: "Online Banking",
        date: "2021-10-10",
        filename: "C:\\Users\\test\\Documents\\ein\\wirklich\\sehr\\langer\\pfad\\test2.fp",
    },
    {
        name: "Arbeit",
        date: "2021-10-10",
        filename: "C:\\Users\\test\\Documents\\test3.fp",
    },
]

const RecentDB = ({ data }) => {
    return (
        <div className="recentDB">
            <div className="name">{data.name}</div>
            <div className="filename">{data.filename}</div>
        </div>
    );
}


const LoginPage = ({ setDb, setLogin }) => {
    

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

    const openFileOption = {
        component: () => {
            return (
                <div className="openFileItem recentDB">
                    Open File...
                </div>
            );
        },
        onClick: () => {
            console.log("File:", backend.selectDBFile());
        },
    }

    return (
        <div className="loginPage">
           <TitleBar/>

            <div className="loginForm-wrapper">
                <div className="loginForm">
                    <FirstpassLogo className="firstpassLogo" />

                    <div className="loginFormInputs">
                        <div className="databaseInput">
                        <DropdownMenu
                            options={recentDBs}
                            placeholder={
                                <span style={{ paddingLeft: "10px" }}>
                                    Select a database...
                                </span>
                            }
                            icon={<DnsRounded />}
                            component={RecentDB}
                            customItems={[openFileOption]}
                        />
                         <Button onClick={() => setLogin(false)}>{<AddRounded />}</Button>
                         </div>
                        <FormInput
                            placeholder="Enter Masterpassword"
                            type="password"
                            iconLeft={<KeyRounded />}
                        />
                    </div>

                    <Button onClick={login}>Login</Button>
                </div>
            </div>
            <WavesSvg />
        </div>
    );
};

export default LoginPage