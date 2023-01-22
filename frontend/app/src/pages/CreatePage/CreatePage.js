import React, { useContext, useEffect, useState } from "react"
import "./CreatePage.less"

import FirstpassLogo from "assets/svg/logo_full.svg"
import WavesSvg from "assets/svg/waves.svg"

import { FormInput, TitleBar, Button, ErrorMessage, PasswordStrength } from "components"

import AppContext from "contexts/App.context"

import {
    KeyRounded,
    MoreHorizRounded,
    InsertDriveFileRounded,
    ArrowBackIosRounded,
    FormatSizeRounded,
    CheckCircleRounded,
    CancelRounded,
    CheckRounded,
    CloseRounded
} from "@mui/icons-material";

import backend from "backend"
import useShortcut from "hooks/useShortcut"


function sanitizeName(name) {
    return name.toLowerCase().trim()
        .replace(/[\s_-]+/g, "_")
        .replace(/\W/g, "");
}

const CreatePage = () => {
    
    const { setDb, setLogin } = useContext(AppContext);
    const [error, setError] = useState(null);

    const [state, setState] = useState({
        masterpassword: "",
        masterpassword2: "",
        filepath: "",
        name: ""
    });
    const [editedFilepath, setEditedFilepath] = useState(false);

    function update(e) {
        setState(s => ({ ...s, [e.target.name]: e.target.value }));
    }

    useEffect(() => {
        if (!editedFilepath) {
            backend.getDocumentsFolder().then(documents => {
                const filepath = documents + "/" + (state.name ? sanitizeName(state.name) : "vault") + ".fpdb";
                setState(s => ({ ...s, filepath }));
            });
        }
    }, [state.name]);

    async function create() {
        const { masterpassword, masterpassword2, filepath, name } = state;

        if (name === "") return setError({code: 400, message: "Name cannot be empty"});
        if (filepath === "") return setError({code: 400, message: "Filepath cannot be empty"});
        if (masterpassword === "") return setError({code: 400, message: "Masterpassword cannot be empty"});

        if (masterpassword !== masterpassword2) {
            setError({code: 400, message: "Passwords don't match"});
            return;
        }
    
        try {
            const { data: db } = await backend.call({
                type: "CREATE_DB",
                data: {
                    masterpassword,
                    filepath,
                    name
                }
            });
            setDb(db);
            setLogin(true);
    
        } catch (e) {
            console.error(e);
            setError(e);
        }
    
    }

    useShortcut("Enter", create);

    async function selectFilePath() {
        const filepath = await backend.openFile({
            title: "Select Vault Path",
            defaultPath: state.filepath,
            buttonLabel: "Select",
            filters: [ backend.FileFilter.Vault ],
        });
        if (filepath) setState(s => ({ ...s, filepath }));
    }


    return (
        <div className="createPage">
           <TitleBar/>

            <div className="createForm-wrapper">
                <div className="createForm">
                    <div className="createPageTitle">
                        <FirstpassLogo className="firstpassLogo" />
                    </div>


                    <div className="createFormInputs">
                        {error && <ErrorMessage error={error} />}
                        <FormInput
                                placeholder="Enter Vault Name"
                                type="text"
                                className="nameInput"
                                iconLeft={<FormatSizeRounded />}
                                autoFocus={true}
                                value={state.name}
                                onInput={update}
                                name="name"
                            />
                        <div className="databaseInput">
                            <FormInput
                                placeholder="Enter Vault Path"
                                type="text"
                                className="dbPathInput"
                                iconLeft={<InsertDriveFileRounded />}
                                value={state.filepath}
                                onInput={e => {
                                    setEditedFilepath(true);
                                    update(e);
                                }}
                                name="filepath"
                            />
                            <Button type="tertiary" onClick={selectFilePath} >{<MoreHorizRounded />}</Button>
                        </div>
                        <FormInput
                            className="masterpasswordA"
                            placeholder="Enter Masterpassword"
                            type="password"
                            iconLeft={<KeyRounded />}
                            value={state.masterpassword}
                            onInput={update}
                            name="masterpassword"
                        />
                        <FormInput
                            className="masterpasswordB"
                            placeholder="Enter Masterpassword again"
                            type="password"
                            iconLeft={<KeyRounded />}
                            value={state.masterpassword2}
                            onInput={update}
                            name="masterpassword2"
                            iconRight={state.masterpassword2 && (
                                state.masterpassword === state.masterpassword2 ? (
                                    <CheckRounded style={{ fill:"var(--success)" }} />
                                ) : (
                                    <CloseRounded style={{ fill:"var(--error)" }} />)
                            )}
                        />
                        <PasswordStrength password={state.masterpassword} />
                    </div>
                    <div className="buttons">
                        <Button type="tertiary" id="back" onClick={() => setLogin(true)}>{<ArrowBackIosRounded/>}</Button>
                        <Button onClick={create} id="create">Create</Button>
                    </div>
                </div>
            </div>
            <WavesSvg className="wavesSvg" />
        </div>
    );
};

export default CreatePage