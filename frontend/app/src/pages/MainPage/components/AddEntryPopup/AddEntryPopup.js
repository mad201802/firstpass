import React from "react";
import "./AddEntryPopup.less";
import { CloseRounded,LinkRounded, PersonRounded,KeyRounded } from "@mui/icons-material";

import { Button,FormInput } from "components";


const AddEntryPopup = ({ setAddEntryPopupVisible }) => {
  return (
    <div className="entryPopupRapper">
      <div className="entryPopup">
        <div id="closeButton" onClick={() => setAddEntryPopupVisible(false)}>
          {" "}
          <CloseRounded id="close" />{" "}
        </div>
        <div className="entryPopupContent">
          <h1>Add Entry</h1>
          <FormInput
            placeholder="URL"
            autoFocus={true}
            iconLeft={<LinkRounded />}
          />
          <FormInput placeholder="Username" iconLeft={<PersonRounded />} />
          <FormInput placeholder="Password" iconLeft={<KeyRounded />} />
          <div className="buttonBox">
            <Button className="cancelButton" onClick={()=> setAddEntryPopupVisible(false)}>Cancel</Button>
            <Button className="addEntryButton">Add</Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddEntryPopup;
