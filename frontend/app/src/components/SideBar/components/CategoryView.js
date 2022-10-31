import React, { useState, useEffect, useRef } from 'react'
import { KeyboardArrowUpRounded, WebRounded } from "@mui/icons-material";

import "./CategoryView.less";


const CategoryView = ({ name, items }) => {

    const [ opened, setOpened ] = useState(false);

    return (
        <div className={opened ? "category opened" : "category"}>
            <span className="categoryHeader" onClick={() => setOpened(v => !v)}>
                <WebRounded className="categoryIcon" />
                <span className="categoryName">{name}</span>
                <KeyboardArrowUpRounded className="expandedViewIcon"/>
            </span>
            <div
                className="categoryItems"
                style={{maxHeight: `calc(${items.length} * (var(--categoryItemHeight) + 5px) + 10px`}}
            >
                {items.map((item, i) => {
                    return (
                        <div key={i} className="categoryItem">
                            <div className="siteIcon">
                                <img src={item.url + "/favicon.ico"} className="categoryItemIcon"/>
                                </div>
                            <p>{item.name}</p>
                        </div>
                    );
                })}
            </div>
        </div>
    );
}

export default CategoryView