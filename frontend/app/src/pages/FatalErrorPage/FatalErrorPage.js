import React from "react"
import "./FatalErrorPage.less"
import { ErrorMessage, TitleBar } from "components"

const FatalErrorPage = ({ error }) => {
  return (
    <div className="fatalErrorPage">
        <TitleBar />
        <div className="fatalErrorPage-content">
            <h1 className="fatalErrorPage-title">Fatal Error</h1>
            <ErrorMessage error={error} showCode={true} />
            <p className="fatalErrorPage-message">
                An unexpected error has occurred. Please restart the application.<br />
                If the error persists, please contact the developer.
            </p>
        </div>
    </div>
  )
}

export default FatalErrorPage