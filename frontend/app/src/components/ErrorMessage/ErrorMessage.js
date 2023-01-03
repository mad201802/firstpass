import React from "react"
import "./ErrorMessage.less"

const ErrorMessage = ({error}) => {
  return (
    <div className="errorMessage"><span>{error.code}</span>{error.message}</div>
  )
}

export default ErrorMessage