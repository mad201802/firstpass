import React, { useContext, useEffect, useState } from "react";
import "./PasswordListView.less";
import AppContext from "contexts/App.context";
import { FormInput } from "components";

import { SearchRounded } from "@mui/icons-material";

const PasswordListView = ({ currentCategory }) => {
    const { db } = useContext(AppContext);
    const category = db.categories[currentCategory];


    const [entries, setEntries] = useState(null);
    const [searchTerm, setSearchTerm] = useState("");

    useEffect(() => {
        setEntries(db.entries.filter(
            (entry) => entry.category === category.id
        ));
    }, [db.entries, category.id]);

    return (
        <div className="passwordListView">
            <div className="toolbar">
                <span>
                    {category.category} - {entries?.length || "0"} Entries
                </span>
                <FormInput
                    className="searchInput"
                    placeholder="Search"
                    iconLeft={<SearchRounded />}
                    value={searchTerm}
                    onInput={(e) => setSearchTerm(e.target.value)}
                />
            </div>
        </div>
    );
};

export default PasswordListView;
