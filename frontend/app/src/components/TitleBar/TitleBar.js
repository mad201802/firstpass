import React from "react";
import "./TitleBar.less";
import { CloseRounded, Crop54Rounded, RemoveRounded } from "@mui/icons-material";
import backend from "../../backend";

const TitleBar = () => {
    return (
        <div className="titleBar">
            <div className="dragqueen"></div>
            <div id="windowControls">
                <div id="minimizebox" onClick={backend.minimize}>
                    <RemoveRounded id="minimize" />
                </div>
                <div id="maximizebox" onClick={backend.maximize}>
                    <Crop54Rounded id="maximize" />
                </div>
                <div id="closebox" onClick={backend.close}>
                    <CloseRounded id="close" />
                </div>
            </div>
        </div>
    );
};

export default TitleBar;
