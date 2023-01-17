import React, { useState, useEffect, useRef } from "react";

import { ChevronRightRounded } from "@mui/icons-material";

import "./DropdownMenu.less";

const DropdownMenu = ({
    value: currentValue,
    onChange: setCurrentValue,
    onChange,
    options,
    placeholder,
    component: DropdownItem,
    icon,
    customItems
}) => {
    // const [currentValue, setCurrentValue] = useState(df);
    const [isOpen, setIsOpen] = useState(false);

    const selectOption = (value) => {
        onChange && onChange(value, options[value]);
        setCurrentValue(value);
        setIsOpen(false);
    };

    const optionListRef = useRef();
    useEffect(() => {
        // check if optionList is overflowing the window
        const optionList = optionListRef.current;
        const optionListRect = optionList.getBoundingClientRect();

        if (optionListRect.bottom > window.innerHeight) {
            const newHeight = window.innerHeight - optionListRect.top - 10;
            optionList.style.maxHeight = newHeight + "px";
        }

    }, [options, customItems, window.innerHeight]);
    

    return (
        <div className="dropdown" onClick={(e) => setIsOpen(!isOpen)}>
            {icon && <div className="dropdownIcon">{icon}</div>}

            <div className="currentOption">
                {!isNaN(currentValue) ? (
                    <DropdownItem data={options[currentValue]} />
                ) : (
                    placeholder
                )}
            </div>

            <ChevronRightRounded className="dropdownCaret" data-open={isOpen} />

            <div className="dropdownOptionList" data-open={isOpen} ref={optionListRef}>
                {Object.entries(options).map(([v, n]) => (
                    <div
                        className="dropdownOption"
                        key={v}
                        onClick={() => selectOption(v)}
                    >
                        <DropdownItem data={n} />
                    </div>
                ))}
                {customItems.map((item, i) => (
                    <div
                        className="dropdownOption"
                        key={i}
                        onClick={() => item.onClick()}
                    >
                        {<item.component />}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default DropdownMenu;
