import React from "react"
import "./MainPage.less"

import SideBar from "./components/SideBar/SideBar"
import TitleBar from "../../components/TitleBar/TitleBar"
import PasswordListView from "./components/PasswordListView/PasswordListView"

const MainPage = ({ db, setDb }) => {

  const [currentCategory, setCurrentCategory] = React.useState(0);

  return (
    <div className="mainPage">
      <SideBar
        db={db} setDb={setDb}
        currentCategory={currentCategory}
        setCurrentCategory={setCurrentCategory}
      />

      <div className="mainPageContent">
        <TitleBar />
        <PasswordListView db={db} currentCategory={currentCategory} />
      </div>



    </div>
  )
}

export default MainPage