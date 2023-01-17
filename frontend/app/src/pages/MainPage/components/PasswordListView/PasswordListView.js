import React, { useContext, useEffect, useState } from "react";
import "./PasswordListView.less";
import AppContext from "contexts/App.context";
import { Button, FormInput } from "components";

import PasswordListItem from "./PasswordListItem";

import { AddRounded, ArrowUpwardRounded, SearchRounded } from "@mui/icons-material";

// import _entries from "./mock_entries.json";

function matchesSearch(entry, searchTerm) {
    return (
        searchTerm === "" ||
        entry.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        entry.url?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        entry.username?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        entry.notes?.toLowerCase().includes(searchTerm.toLowerCase())
    )
}

const PasswordListView = ({ currentCategory, setAddEntryPopupVisible }) => {
    const { db } = useContext(AppContext);
    // db.entries = _entries;
    const category = db.categories.find((category) => category.id === currentCategory);

    console.log("rendering password list view, category" + category.id)
    const [entries, setEntries] = useState(null);

    useEffect(() => {
        setEntries(db.entries.filter((entry) => entry.category == category.id));
    }, [db, category]);

    const [searchTerm, setSearchTerm] = useState(""); 

    return (
        <div className="passwordListView">
            <div className="toolbar">
                <Button className="addButton" onClick={() => setAddEntryPopupVisible(true)}>
                    <AddRounded />
                    New
                </Button>
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
            <div className="sortingOptions">
                <div className="sortOption">
                    <span>Name</span>
                    <ArrowUpwardRounded />
                </div>
                <div className="sortOption">
                    <span>Date</span>
                </div>
                <div className="sortOption">
                    <span>Type</span>
                </div>
                <div className="sortOption">
                    <span>Last Used</span>
                </div>
            </div>
            <div className="passwordList">
                {entries?.map((entry, i) => (
                    <PasswordListItem key={i} entry={entry} visible={matchesSearch(entry, searchTerm)} />
                ))}
                {entries?.length === 0 && (
                    <div className="noEntries">
                        <h2>No entries in this category</h2>
                    </div>
                )}
            </div>
        </div>
    );
};

export default PasswordListView;
