import React, { useState, useEffect, useRef } from "react";

import { ChevronRightRounded } from "@mui/icons-material";

import "./DropdownMenu.less";
import useEventListener from "hooks/useEventListener";
import useShortcut from "hooks/useShortcut";

/**
 * A dropdown menu component
 * @param {object} props The props
 * @param {string} props.value The current value of the dropdown menu
 * @param {function} props.onChange The function to call when the value is changed
 * @param {object} props.options The options of the dropdown menu
 * @param {string} props.placeholder The placeholder of the dropdown menu
 * @param {function} props.component The component to use for each option
 * @param {object} props.icon The icon to use for the dropdown menu
 * @param {object} props.customItems The custom items to add to the dropdown menu
 * @param {boolean} props.hideSelected Whether to hide the selected option in the dropdown option list
 * @returns The dropdown menu component
 */
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

    useEventListener("click", e => {
        if (!isOpen) return;
        if (e.target.closest(".dropdown")) return;
        setIsOpen(false);
    })

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
