import React, { useContext } from "react";
import "./PasswordListView.less";
import AppContext from "contexts/App.context";
import { FormInput } from "components";

const PasswordListView = ({ currentCategory }) => {
    const { db } = useContext(AppContext);
    const category = db.categories[currentCategory];
    const entries = db.entries.filter((entry) => entry.category === category.id)

    return (
        <div className="passwordListView">
            <div className="toolbar">
                <p>{category.category}: {entries.length} Entries</p>
                <FormInput placeholder="Search" />
            </div>
        </div>
    );
};

export default PasswordListView;
