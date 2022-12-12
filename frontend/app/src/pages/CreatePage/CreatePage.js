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
        <div className="createPage">
           <TitleBar/>

            <div className="createForm-wrapper">
                <div className="createForm">
                    <FirstpassLogo className="firstpassLogo" />

                    <div className="createFormInputs">
                        <div className="databaseInput">
                            <FormInput
                                placeholder="Enter Database Path"
                                type="text"
                                className="dbPathInput"
                                iconLeft={<InsertDriveFileRounded />}
                            />
                            <Button>{<MoreHorizRounded />}</Button>
                        </div>
                        <FormInput
                            placeholder="Enter Masterpassword"
                            type="password"
                            iconLeft={<KeyRounded />}
                        />
                        <FormInput
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