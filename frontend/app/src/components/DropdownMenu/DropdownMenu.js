import React, { useState, useEffect, useRef } from "react";

import { ChevronRightRounded } from "@mui/icons-material";

import "./DropdownMenu.less";
import useEventListener from "hooks/useEventListener";
import useShortcut from "hooks/useShortcut";

const DropdownMenu = ({
    value: currentValue,
    onChange,
    options,
    placeholder,
    component: DropdownItem,
    icon,
    customItems,
    hideSelected=false
}) => {
    // const [currentValue, setCurrentValue] = useState(df);
    const [isOpen, setIsOpen] = useState(false);

    const selectOption = (value) => {
        onChange && onChange(value, options[value]);
        setIsOpen(false);
    };

    const [resizeTimeout, setResizeTimeout] = useState(null);

    const optionListRef = useRef();

    // close dropdown on window resize to prevent bugs
    useEventListener("resize", () => {
        setIsOpen(false);
        optionListRef.current.style.maxHeight = null;
    }, window);

    useEffect(() => {
        if (!isOpen) return;
        if (resizeTimeout) return;
        // check if optionList is overflowing the window
        // has to be delayed because optionList has not reached full size yet
        setResizeTimeout(setTimeout(() => {
            const optionListRect = optionListRef.current.getBoundingClientRect();

            if (optionListRect.bottom > window.innerHeight) {
                const newHeight = window.innerHeight - optionListRect.top - 10;
                optionListRef.current.style.maxHeight = newHeight + "px";
            }
        setResizeTimeout(null);
        }, 100));

    }, [options, customItems, window.innerHeight, isOpen]);

    useShortcut("Escape", () => setIsOpen(false), isOpen);
    

    return (
        <div className="dropdown" onClick={(e) => setIsOpen(!isOpen)}>
            {icon && <div className="dropdownIcon">{icon}</div>}

            <div className="currentOption">
                {currentValue !== null ? (
                    <DropdownItem data={options[currentValue]} current={true} />
                ) : (
                    placeholder
                )}
            </div>

            <ChevronRightRounded className="dropdownCaret" data-open={isOpen} />

            <div className="dropdownOptionList" data-open={isOpen} ref={optionListRef}>
                {Object.entries(options)
                .filter(([v, n]) => !hideSelected || v != currentValue)
                .map(([v, n]) => (
                    <div
                        className="dropdownOption"
                        key={v}
                        onClick={() => selectOption(v)}
                    >
                        <DropdownItem data={n} />
                    </div>
                ))}
                {customItems?.map((item, i) => (
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
