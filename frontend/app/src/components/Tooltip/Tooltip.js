import React from "react";
import "./Tooltip.less";

/**
 * A custom tooltip
 * @param {object} props The props
 * @param {string} props.children The content to show tooltips for
 * @param {string} props.label The tooltip's label
 * @param {string} props.position The tooltip's position relative to the content
 * @returns The tooltip component
 */
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
