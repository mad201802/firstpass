import React, { useContext, useState } from "react"
import "./CreatePage.less"

import FirstpassLogo from "assets/svg/logo_full.svg"
import WavesSvg from "assets/svg/waves.svg"

import { FormInput, TitleBar, Button, ErrorMessage } from "components"

import AppContext from "contexts/App.context"

import {
    KeyRounded,
    MoreHorizRounded,
    InsertDriveFileRounded,
    ArrowBackIosRounded,
    FormatSizeRounded
} from "@mui/icons-material";

import backend from "backend"
import useShortcut from "hooks/useShortcut"


const CreatePage = () => {
    
    const { setDb, setLogin } = useContext(AppContext);
    const [error, setError] = useState(null);

    async function create() {
        // TODO refactor this so input states are stored with useState in this component
        const masterpassword = document.querySelector(".masterpasswordA input").value.trim();
        const masterpassword2 = document.querySelector(".masterpasswordB input").value.trim();
        const filepath = document.querySelector(".dbPathInput input").value.trim();
        const name = document.querySelector(".nameInput input").value.trim();

        // TODO: Show error message
        if (masterpassword !== masterpassword2) {
            console.log("Passwords don't match");
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
    
        } catch (e) {
            console.log(e);
            setError(e);
            // TODO: Show error message
        }
    
    }

    useShortcut("Enter", create);

    async function selectFilePath() {
        const filepath = await backend.selectDBFile("save");
        if (filepath) document.querySelector(".dbPathInput input").value = filepath;
    }


    return (
        <div className="createPage">
           <TitleBar/>

            <div className="createForm-wrapper">
                <div className="createForm">
                    <div className="createPageTitle">
                        <FirstpassLogo className="firstpassLogo" />
                        <p>Create a new Database</p>
                    </div>


                    <div className="createFormInputs">
                        {error && <ErrorMessage error={error} />}
                        <FormInput
                                placeholder="Enter Vault Name"
                                type="text"
                                className="nameInput"
                                iconLeft={<FormatSizeRounded />}
                                autoFocus={true}
                            />
                        <div className="databaseInput">
                            <FormInput
                                placeholder="Enter Vault Path"
                                type="text"
                                className="dbPathInput"
                                iconLeft={<InsertDriveFileRounded />}
                            />
                            <Button onClick={selectFilePath} >{<MoreHorizRounded />}</Button>
                        </div>
                        <FormInput
                            className="masterpasswordA"
                            placeholder="Enter Masterpassword"
                            type="password"
                            iconLeft={<KeyRounded />}
                        />
                        <FormInput
                            className="masterpasswordB"
                            placeholder="Enter Masterpassword again"
                            type="password"
                            iconLeft={<KeyRounded />}
                        />
                    </div>
                    <div className="buttons">
                        <Button id="back" onClick={() => setLogin(true)}>{<ArrowBackIosRounded/>}</Button>
                    <Button onClick={create} id="create">Create</Button>
                    </div>
                </div>
            </div>
            <WavesSvg className="wavesSvg" />
        </div>
    );
};

export default CreatePage