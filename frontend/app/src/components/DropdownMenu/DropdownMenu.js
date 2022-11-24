import React, { useState } from 'react'

import { ChevronRightRounded, DnsRounded } from '@mui/icons-material';

import "./DropdownMenu.less";

const DropdownMenu = ({ default: df, onChange, options, placeholder, component: DropdownItem, icon }) => {

    const [currentValue, setCurrentValue] = useState(df);
    const [isOpen, setIsOpen] = useState(false);

    const selectOption = (value) => {
        onChange && onChange(value, options[value]);
        setCurrentValue(value);
        setIsOpen(false);
    }

    return (
            <div className="dropdown" onClick={(e) => setIsOpen(!isOpen) } >

                {icon && <div className="dropdownIcon">{icon}</div>}

                <div className="currentOption">
                    {!isNaN(currentValue)
                        ? <DropdownItem data={options[currentValue]} />
                        : placeholder}
                </div>

                <ChevronRightRounded className="dropdownCaret" data-open={isOpen} />

                <div className="dropdownOptionList" data-open={isOpen}>
                    {Object.entries(options).map(([v, n]) => (
                        <div className="dropdownOption" key={v} onClick={() => selectOption(v)}>
                            <DropdownItem data={n} />
                        </div>
                    ))}
                </div>
            </div>
    );
}

export default DropdownMenu
