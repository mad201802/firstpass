import React from "react"
import "./AnimatedPatternBG.less"

import FirstpassIconUrl from "assets/img/logo-pattern.png"

/**
 * An animated background pattern
 * @returns The animated background pattern
 */
const AnimatedPatternBG = () => {
  return (
        <div
            className="patternBg"
            style={{ backgroundImage: `url(${FirstpassIconUrl})` }}
        />
    );
}

export default AnimatedPatternBG