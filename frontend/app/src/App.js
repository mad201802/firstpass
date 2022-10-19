import React from 'react'

import "./App.less";
import SideBar from './components/SideBar/SideBar';
import TitleBar from './components/TitleBar/TitleBar';

const App = () => {
  return (
    <>
        <TitleBar />
        <div id="app-content-wrapper">
          <SideBar />
        </div>
    </>
  )
}

export default App