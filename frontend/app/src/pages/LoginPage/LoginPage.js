import React, { useEffect, useContext, useState } from "react"
import "./LoginPage.less"

import FirstpassLogo from "assets/svg/logo_full.svg"
import WavesSvg from "assets/svg/waves.svg"

import { Button, DropdownMenu, TitleBar, FormInput, ErrorMessage } from "components";
import AppContext from "contexts/App.context"
import backend from "backend"

import { KeyRounded, DnsRounded, AddRounded } from "@mui/icons-material";


const recentDBs_ = [
    {
        name: "Tom's DB",
        date: "2021-10-10",
        filepath: "C:\\Users\\tomfl\\Documents\\test.fpdb",
    },
    {
        name: "Avaze' DB",
        date: "2021-10-10",
        filepath: "C:\\Users\\Avaze\\Documents\\test.fpdb",
    },
    {
        name: "Maurice' DB",
        date: "2021-10-10",
        filepath: "C:\\Users\\mauri\\OneDrive\\Dokumente\\test.fpdb",
    },
    {
        name: "Online Banking",
        date: "2021-10-10",
        filepath: "C:\\Users\\test\\Documents\\ein\\wirklich\\sehr\\langer\\pfad\\test2.fp",
    },
    {
        name: "Arbeit",
        date: "2021-10-10",
        filepath: "C:\\Users\\test\\Documents\\test3.fp",
    },
]

const RecentDB = ({ data }) => {
    return (
        <div className="recentDB">
            <div className="name">{data.name}</div>
            <div className="filename">{data.filepath}</div>
        </div>
    );
}


const LoginPage = () => {

    const { setDb, setLogin } = useContext(AppContext);

    const [database, setDatabase] = useState();
    const [recentDBs, setRecentDBs] = useState([]);

    const [masterpassword, setMasterpassword] = useState("");
    const [error, setError] = useState(null);

    useEffect(() => {
        // TODO: Load recent DBs from backend
        backend.call({
            type: "LIST_RECENT_DBS",
            data: {},
        }).then((dbs) => {
            setRecentDBs(dbs.data.recentDBS);
        });
        // setRecentDBs(recentDBs_);
    }, []);
    

    async function login() {
        const filepath = recentDBs[database].filepath;
    
        try {
            const { data: db } = await backend.call({
                type: "OPEN_DB",
                data: {
                    masterpassword,
                    filepath,
                }
            });
            console.log(db);
            setDb(db);
    
        } catch (e) {
            // TODO Error Handling
            console.log(e);
            setError(e);
            // setDb({
            //     categories: ["test", "1234", "wow"],
            // });
        }
    
    }

    useEffect(() => {
        const handler = e => {
            if (e.key === "Enter") {
                login();
            }
        }
        document.addEventListener("keydown", handler);

        return () => {
            document.removeEventListener("keydown", handler);
        }
    })

    const openFileOption = {
        component: () => {
            return (
                <div className="openFileItem recentDB">
                    Open File...
                </div>
            );
        },
        onClick: () => {
            // TODO create a new entry in recent dbs from filepath
            console.log("File:", backend.selectDBFile());
        },
    }

    return (
        <div className="loginPage">
           <TitleBar />

            <div className="loginForm-wrapper">
                <div className="loginForm">
                    <FirstpassLogo className="firstpassLogo" />

                    <div className="loginFormInputs">
                        {error && <ErrorMessage error={error} />}
                        <div className="databaseInput">
                        <DropdownMenu
                            options={recentDBs}
                            value={database}
                            onChange={db => {
                                setDatabase(db);
                                document.querySelector(".masterpasswordInput input").focus();
                            }}
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
                            autoFocus={true}
                            className="masterpasswordInput"
                            value={masterpassword}
                            onInput={e => {
                                setMasterpassword(e.target.value.trim());
                            }}
                        />
                    </div>

                    <Button id="loginButton" onClick={login}>Login</Button>
                </div>
            </div>
            <WavesSvg className="wavesSvg" />
        </div>
    );
};

export default LoginPage