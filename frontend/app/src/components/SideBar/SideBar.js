import React, { useState } from 'react'
import { KeyboardArrowUpRounded, OpenWithRounded } from '@mui/icons-material';

import "./SideBar.less"
import CategoryView from "./components/CategoryView";

import data from "./sampleData.json";


const SideBar = () => {
  return (
    <div id="sidebar">
        {Object.keys(data.passwords).map((category, i) => {
          return (
            <CategoryView
              name={category}
              items={data.passwords[category]}
              key={i}
            />
          );
        })}
    </div>
  )
}

export default SideBar