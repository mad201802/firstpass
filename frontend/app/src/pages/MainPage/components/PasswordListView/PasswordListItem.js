import React from "react"
import "./PasswordListItem.less"

const PasswordListItem = ({ entry }) => {
  return (
    <div className="passwordListItem">{entry.username} - {entry.password}</div>
  )
}

export default PasswordListItem