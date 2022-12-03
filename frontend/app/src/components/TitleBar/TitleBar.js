import React from 'react'

import { FullscreenRounded, FullscreenExitRounded, CloseRounded } from '@mui/icons-material';

import "./TitleBar.less";


const TitleBar = () => {
  return (
    <div className='title-bar'>
        <div id="drag-area">Firstpass</div>
        <div id="window-btns">
            <button id="window-minimize-btn" onClick={app.minimize}><FullscreenExitRounded /></button>
            <button id="window-maximize-btn" onClick={app.maximize}><FullscreenRounded /></button>
            <button id="window-close-btn" onClick={app.close}><CloseRounded /></button>
        </div>
    </div>
  )
}

export default TitleBar