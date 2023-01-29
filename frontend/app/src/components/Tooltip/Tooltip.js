import React from "react";
import "./Tooltip.less";

const Tooltip = ({children, label, position="center"}) => {

    // const [isHover, setIsHover] = useState(false);

    return (
        <div className="tooltipWrapper">
            {children}
            <span className="tooltip" data-position={position} >{label}</span>
        </div>
    );
};

export default Tooltip;
