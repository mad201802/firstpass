import React, { useContext } from "react";
import "./PasswordListView.less";
import AppContext from "contexts/App.context";

const PasswordListView = ({ currentCategory }) => {
    const { db } = useContext(AppContext);

    return (
        <div className="passwordListView">
            Category {currentCategory},{" "}
            {db.categories[currentCategory].category}
        </div>
    );
};

export default PasswordListView;
