import React from "react"
import "./PasswordListView.less"

const PasswordListView = ({ db, currentCategory }) => {
  return (
    <div className="passwordListView">
      Category {currentCategory}, {db.categories[currentCategory].category}
    </div>
  )
}

export default PasswordListView