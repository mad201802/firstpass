// const electron = require("electron");
const { ipcRenderer } = electron;


/**
 * @typedef {Object} IPCRequest
 * @property {string} type
 * @property {Object} data
 */

/**
 * @typedef {Object} IPCError
 * @property {string} id
 * @property {string} message
 * @property {number} code
 */

/**
 * @typedef {Object} IPCErrorResponse
 * @property {IPCError} error
 */


/**
 * @callback ErrorHandler
 * @param {IPCErrorResponse} data
 */

let errorHandler = () => {};
ipcRenderer.on("backend-error", (e, data) => errorHandler(data));


/** Minimize the app window */
const minimize = () => ipcRenderer.send("minimize");
/** Maximize the app window */
const maximize = () => ipcRenderer.send("maximize");
/** Close the app */
const close = () => ipcRenderer.send("close");

/**
 * Set an Error Handler for unhandled backend errors
 * @param {ErrorHandler} callback The callback to call when an error occurs
 */
const onError = (callback) => errorHandler = callback;

/**
 * Call a function in the backend
 * @param {IPCRequest} data 
 * @returns {Promise<IPCErrorResponse|Object>} The response from the backend
 */
const call = async (data) => {
    let res;
    try {
        res = JSON.parse(await ipcRenderer.invoke("call", data));
    } catch (e) {
        console.log(e)
        throw { message: "Backend returned invalid message", code: 500 }
    }
    if (res.error) throw res.error;
    return res;
}

/**
 * Show a dialog to open/save a db file
 * @returns {Promise<string[]|string|undefined>} The selected files
 */
function selectDBFile(type = "open") {
    switch(type) {
        case "open":
            return ipcRenderer.invoke("showOpenDialog", {
                title: "Select a Firstpass Database",
                properties: ["openFile"],
                filters: [
                    { name: "Firstpass Database", extensions: ["fpdb"] },
                ],
            });

        case "save":
            return ipcRenderer.invoke("showSaveDialog", {
                title: "Select Database Location",
                filters: [
                    { name: "Firstpass Database", extensions: ["fpdb"] },
                ],
            });
    }
}

/**
 * Control the application's backend
 */
export default {
    minimize,
    maximize,
    close,
    onError,
    call,
    selectDBFile,
};