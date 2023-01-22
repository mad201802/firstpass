import React, { useEffect, useContext, useState } from "react"
import "./LoginPage.less"

import FirstpassLogo from "assets/svg/logo_full.svg"
import WavesSvg from "assets/svg/waves.svg"

import { Button, DropdownMenu, TitleBar, FormInput, ErrorMessage } from "components";
import AppContext from "contexts/App.context"
import backend from "backend"

import { KeyRounded, DnsRounded, AddRounded } from "@mui/icons-material";
import useShortcut from "hooks/useShortcut";


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

    const [database, setDatabase] = useState(null);
    const [recentDBs, setRecentDBs] = useState([]);

    const [masterpassword, setMasterpassword] = useState("");
    const [error, setError] = useState(null);

    useEffect(() => {
        backend.call({
            type: "LIST_RECENT_DBS",
            data: {},
        }).then(({ data }) => {
            setRecentDBs(data.recentDBs);
        });
    }, []);
    

    async function login() {
        if (database === null) return setError({code: 400, message: "No database selected"});
        if (masterpassword === "") return setError({code: 400, message: "Masterpassword cannot be empty"});
        const filepath = recentDBs[database].filepath;
    
        try {
            const { data: db } = await backend.call({
                type: "OPEN_DB",
                data: {
                    masterpassword,
                    filepath,
                }
            });
            setDb(db);
    
        } catch (e) {
            setError(e);
        }
    
    }

    useShortcut("Enter", login);

    const openFileOption = {
        component: () => {
            return (
                <div className="openFileItem recentDB">
                    Open File...
                </div>
            );
        },
        onClick: async () => {
            let db_files = await backend.openFile({ 
                filters: [ backend.FileFilter.Vault ],
                title: "Open Database",
                multi: true,
            });
            if (!db_files) return;

            db_files = db_files.map(s => s.replace(/\\/g, "/")).filter(file => !recentDBs.find(db => db.filepath === file));
            
            if (db_files.length === 0) return;
            try {
                for (const file of db_files) {
                    const { data: db_info } = await backend.call({
                        type: "ADD_RECENT_DB",
                        data: {
                            filepath: file,
                        }
                    });
                    setRecentDBs(d => [...d, {...db_info, filepath: file}]);
                }
                setDatabase(recentDBs.length);
                document.querySelector(".masterpasswordInput input").focus();
            } catch (e) {
                setError(e);
            }
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
                         <Button type="tertiary" onClick={() => setLogin(false)}>{<AddRounded />}</Button>
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