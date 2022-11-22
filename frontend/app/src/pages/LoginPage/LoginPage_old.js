import React from "react";
import "./LoginPage.less";

import FirstpassLogo from "../../../assets/svg/logo_small.svg"
import FirstpassLogo2 from "../../../assets/svg/logo_full.svg"
import WaveSvg from "../../../assets/svg/waves.svg";
import FormInput from "../../components/FormInput/FormInput";

import { KeyRounded } from "@mui/icons-material";
import DropdownMenu from "../../components/DropdownMenu/DropdownMenu";


const recentDBs = [
    {
        name: "test",
        date: "2021-10-10",
        filename: "C:\\Users\\test\\Documents\\test.fp",
    },
    {
        name: "test2",
        date: "2021-10-10",
        filename: "C:\\Users\\test\\Documents\\test2.fp",
    },
    {
        name: "test3",
        date: "2021-10-10",
        filename: "C:\\Users\\test\\Documents\\test3.fp",
    },
]

const RecentDB = ({ data }) => {
    return (
        <div className="recentDB">
            <div className="name">{data.name}</div>
            <div className="date">{data.date}</div>
            <div className="filename">{data.filename}</div>
        </div>
    );
}


const LoginPage = ({ setDb }) => {
    function login() {
        setDb({ categories: ["test", "1234", "wow"] });
    }

    return (
        <div className="loginPage">

            <div className="loginPage-form">
                <FirstpassLogo2 width="60%" />

                <DropdownMenu options={recentDBs} placeholder={<>Select a database...</>} component={RecentDB} />

                <FormInput className="masterpassword" placeholder="Enter Master Password" iconLeft={<KeyRounded />} type="password" /> 
            </div>

            <WaveSvg className="wave-svg" />
        </div>
    );
};

export default LoginPage;
