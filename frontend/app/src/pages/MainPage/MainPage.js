import React from "react"
import "./MainPage.less"

const MainPage = ({ db }) => {
  return (
    <div>{JSON.stringify(db, null, 2)}</div>
  )
}

export default MainPage