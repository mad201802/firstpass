import React, { useState } from "react"
import "./UrlLogo.less"

function getDomain(url) {
    return url
        ?.replace(/https?:\/\//i, "")
        ?.split("/")?.[0]
        ?.split(".")
        ?.slice(-2, -1)?.[0];
}

const UrlLogo = ({entry, className, ...props}) => {

    const [imgLoadError, setImgLoadError] = useState(!entry.url?.match(/https?:\/\/.*\..{2,}/i))

  return (
    <div className={"urlLogo " + (className || "")} {...props}>
        {imgLoadError ? (
            <span>{getDomain(entry.url)?.[0] || entry.name[0]}</span>
        ) : (
            <img src={entry.url + "/favicon.ico"} alt="favicon" onError={() => setImgLoadError(true)} />
        )}
    </div>
  )
}

export default UrlLogo