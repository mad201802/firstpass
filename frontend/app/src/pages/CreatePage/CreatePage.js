import React from "react"
import "./CreatePage.less"

import FirstpassLogo from "../../../assets/svg/logo_full.svg"
import WavesSvg from "../../../assets/svg/waves.svg"
import FormInput from "../../components/FormInput/FormInput"

import { KeyRounded, MoreHorizRounded, InsertDriveFileRounded,ArrowBackIosRounded } from "@mui/icons-material"
import Button from "../../components/Button/Button"


import backend from "../../backend"
import TitleBar from "../../components/TitleBar/TitleBar"



const CreatePage = ({ setDb, setLogin }) => {
    

    async function create() {
        const masterpassword = document.querySelector(".masterpasswordA input").value.trim();
        const masterpassword2 = document.querySelector(".masterpasswordB input").value.trim();
        const filepath = document.querySelector(".dbPathInput input").value.trim();

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
                }
            });
            setDb(db);
    
        } catch (e) {
            // TODO: Show error message
        }
    
    }

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
                        <div className="databaseInput">
                            <FormInput
                                placeholder="Enter Database Path"
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
            <WavesSvg />
        </div>
    );
};

export default CreatePage