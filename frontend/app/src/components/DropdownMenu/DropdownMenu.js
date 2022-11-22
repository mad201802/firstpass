import React, { useState } from 'react'

import { ChevronRightRounded } from '@mui/icons-material';

import "./DropdownMenu.less";

const DropdownMenu = ({ default: df, onChange, options, label, placeholder, component: DropdownItem }) => {

    const [currentValue, setCurrentValue] = useState(df);
    const [isOpen, setIsOpen] = useState(false);

    const selectOption = (value) => {
        onChange ?? onChange(value, options[value]);
        setCurrentValue(value);
        setIsOpen(false);
    }

    return (
        <div className="dropdownMenu">
            { label && <span className="label">{label}</span> }
            <div
                className="dropdown"
                onClick={(e) => {
                    if (e.target.className == "dropdownOptionList visible") return;
                    setIsOpen(!isOpen);
                }}
            >
                <div className="currentOption">
                    {!isNaN(currentValue)
                        ? <DropdownItem data={options[currentValue]} />
                        : placeholder}
                </div>
                {/* TODO: use data attributes to style */}
                <ChevronRightRounded className={isOpen ? "active" : ""} />
                <div
                    className={
                        isOpen
                            ? "dropdownOptionList visible"
                            : "dropdownOptionList"
                    }
                >
                    {Object.entries(options).map(([v, n]) => (
                        <DropdownItem key={v} data={n} onClick={() => selectOption(v)} />
                    ))}
                </div>
            </div>
        </div>
    );
}

export default DropdownMenu
