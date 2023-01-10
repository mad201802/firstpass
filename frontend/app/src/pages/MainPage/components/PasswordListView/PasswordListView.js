import React, { useContext, useEffect, useState } from "react";
import "./PasswordListView.less";
import AppContext from "contexts/App.context";
import { Button, FormInput } from "components";

import PasswordListItem from "./PasswordListItem";

import { AddRounded, ArrowUpwardRounded, SearchRounded } from "@mui/icons-material";

const _entries = JSON.parse('[{"category":0,"password":"17a7c18c","username":"test_user522","url":"https://amazon.com"},{"category":0,"password":"d2b34281","username":"test_user069","url":"https://amazon.com"},{"category":0,"password":"edb7cc5d","username":"test_user507","url":"https://amazon.com"},{"category":0,"password":"63296e1e","username":"test_user127","url":"https://amazon.com"},{"category":0,"password":"f23c5163","username":"test_user244","url":"https://amazon.com"},{"category":0,"password":"0fe06f87","username":"test_user041","url":"https://amazon.com"},{"category":0,"password":"07255204","username":"test_user582","url":"https://amazon.com"},{"category":0,"password":"51628786","username":"test_user226","url":"https://amazon.com"},{"category":0,"password":"209b5e90","username":"test_user501","url":"https://amazon.com"},{"category":0,"password":"fbd5ceb6","username":"test_user985","url":"https://amazon.com"},{"category":1,"password":"f12a1bed","username":"test_user626","url":"https://amazon.com"},{"category":1,"password":"471d002c","username":"test_user726","url":"https://amazon.com"},{"category":1,"password":"7e337d01","username":"test_user048","url":"https://amazon.com"},{"category":1,"password":"7d426a1e","username":"test_user523","url":"https://amazon.com"},{"category":1,"password":"9125dad3","username":"test_user843","url":"https://amazon.com"},{"category":1,"password":"0c29be95","username":"test_user235","url":"https://amazon.com"},{"category":1,"password":"b36d1906","username":"test_user115","url":"https://amazon.com"},{"category":1,"password":"6ab25788","username":"test_user394","url":"https://amazon.com"},{"category":1,"password":"1ff68a20","username":"test_user580","url":"https://amazon.com"},{"category":1,"password":"1cbee92f","username":"test_user819","url":"https://amazon.com"},{"category":2,"password":"09fbc989","username":"test_user372","url":"https://amazon.com"},{"category":2,"password":"59ebc21e","username":"test_user575","url":"https://amazon.com"},{"category":2,"password":"bacc60e0","username":"test_user319","url":"https://amazon.com"},{"category":2,"password":"9f064ce8","username":"test_user740","url":"https://amazon.com"},{"category":2,"password":"82ee7d41","username":"test_user937","url":"https://amazon.com"},{"category":2,"password":"17aff6f5","username":"test_user294","url":"https://amazon.com"},{"category":2,"password":"6db58a62","username":"test_user113","url":"https://amazon.com"},{"category":2,"password":"ce7da17d","username":"test_user702","url":"https://amazon.com"},{"category":2,"password":"be228787","username":"test_user269","url":"https://amazon.com"},{"category":2,"password":"b61de89a","username":"test_user519","url":"https://amazon.com"},{"category":3,"password":"fe80ed9b","username":"test_user657","url":"https://amazon.com"},{"category":3,"password":"63401ea9","username":"test_user547","url":"https://amazon.com"},{"category":3,"password":"8d3f6ec4","username":"test_user081","url":"https://amazon.com"},{"category":3,"password":"11051f11","username":"test_user901","url":"https://amazon.com"},{"category":3,"password":"d4cfd246","username":"test_user589","url":"https://amazon.com"},{"category":3,"password":"f9cb7177","username":"test_user042","url":"https://amazon.com"},{"category":3,"password":"ed55403c","username":"test_user749","url":"https://amazon.com"},{"category":3,"password":"ab9ac5f2","username":"test_user798","url":"https://amazon.com"},{"category":3,"password":"c8727f5f","username":"test_user018","url":"https://amazon.com"},{"category":3,"password":"271f11d7","username":"test_user667","url":"https://amazon.com"},{"category":4,"password":"66fac5d6","username":"test_user456","url":"https://amazon.com"},{"category":4,"password":"35fe4a4f","username":"test_user834","url":"https://amazon.com"},{"category":4,"password":"7527a12a","username":"test_user425","url":"https://amazon.com"},{"category":4,"password":"961a171f","username":"test_user829","url":"https://amazon.com"},{"category":4,"password":"63c98243","username":"test_user678","url":"https://amazon.com"},{"category":4,"password":"ffc9c263","username":"test_user161","url":"https://amazon.com"},{"category":4,"password":"d0d21a0c","username":"test_user121","url":"https://amazon.com"},{"category":4,"password":"3ceed7e6","username":"test_user307","url":"https://amazon.com"},{"category":4,"password":"161954c8","username":"test_user120","url":"https://amazon.com"},{"category":4,"password":"91fecb32","username":"test_user929","url":"https://amazon.com"},{"category":5,"password":"8b1fc3ee","username":"test_user556","url":"https://amazon.com"},{"category":5,"password":"2f8cf855","username":"test_user463","url":"https://amazon.com"},{"category":5,"password":"8b6b33a4","username":"test_user108","url":"https://amazon.com"},{"category":5,"password":"bd60665b","username":"test_user388","url":"https://amazon.com"},{"category":5,"password":"397417ce","username":"test_user072","url":"https://amazon.com"},{"category":5,"password":"6144b694","username":"test_user808","url":"https://amazon.com"},{"category":5,"password":"daf07ae9","username":"test_user869","url":"https://amazon.com"},{"category":5,"password":"2e598054","username":"test_user679","url":"https://amazon.com"},{"category":5,"password":"6c9841de","username":"test_user952","url":"https://amazon.com"},{"category":5,"password":"7a149dca","username":"test_user517","url":"https://amazon.com"},{"category":6,"password":"9250ced8","username":"test_user861","url":"https://amazon.com"},{"category":6,"password":"d3b3293b","username":"test_user869","url":"https://amazon.com"},{"category":6,"password":"c4483792","username":"test_user497","url":"https://amazon.com"},{"category":6,"password":"1fab864b","username":"test_user658","url":"https://amazon.com"},{"category":6,"password":"70b13d8f","username":"test_user635","url":"https://amazon.com"},{"category":6,"password":"7a3ae750","username":"test_user769","url":"https://amazon.com"},{"category":6,"password":"4269d1fa","username":"test_user857","url":"https://amazon.com"},{"category":6,"password":"62e517f6","username":"test_user567","url":"https://amazon.com"},{"category":6,"password":"c91e3275","username":"test_user524","url":"https://amazon.com"},{"category":6,"password":"e8fbef4f","username":"test_user716","url":"https://amazon.com"},{"category":7,"password":"11ab0c9c","username":"test_user679","url":"https://amazon.com"},{"category":7,"password":"e56cb6b2","username":"test_user320","url":"https://amazon.com"},{"category":7,"password":"b07d4e0e","username":"test_user677","url":"https://amazon.com"},{"category":7,"password":"aaeb7f6f","username":"test_user268","url":"https://amazon.com"},{"category":7,"password":"097471ce","username":"test_user057","url":"https://amazon.com"},{"category":7,"password":"f1d71fec","username":"test_user077","url":"https://amazon.com"},{"category":7,"password":"fbfea833","username":"test_user954","url":"https://amazon.com"},{"category":7,"password":"405c5c9f","username":"test_user556","url":"https://amazon.com"},{"category":7,"password":"5524c336","username":"test_user388","url":"https://amazon.com"},{"category":7,"password":"9d825156","username":"test_user985","url":"https://amazon.com"}]');

const PasswordListView = ({ currentCategory, setAddEntryPopupVisible }) => {
    const { db } = useContext(AppContext);
    db.entries = _entries;
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
                <Button className="addButton" onClick={() => {setAddEntryPopupVisible(true); console.log("test")}}>
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
                {entries?.map((entry) => (
                    <PasswordListItem key={entry.id} entry={entry} />
                ))}
            </div>
        </div>
    );
};

export default PasswordListView;
