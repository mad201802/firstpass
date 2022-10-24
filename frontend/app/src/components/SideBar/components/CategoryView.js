import React, { useState, useEffect, useRef } from 'react'
import { KeyboardArrowUpRounded } from "@mui/icons-material";

import "./CategoryView.less";


const CategoryView = ({ name, items }) => {

    const [ opened, setOpened ] = useState(false);

    return (
        <div className={opened ? "category opened" : "category"}>
            <span className="categoryHeader" onClick={() => setOpened(v => !v)}>
                <KeyboardArrowUpRounded />
                <span className="categoryName">{name}</span>
            </span>
            <div
                className="categoryItems"
                style={{maxHeight: `calc(${items.length} * var(--categoryItemHeight)`}}
            >
                {items.map((item, i) => {
                    return (
                        <div key={i} className="categoryItem">
                            <p>{item.name}</p>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default CategoryView