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
        throw { message: "Backend returned invalid message", code: 500 }
    }
    if (res.error) throw res.error;
    return res;
}

const FileFilter = {
    Vault: {
        name: "Firstpass Vault",
        extensions: ["fpdb"],
    },
    Theme: {
        name: "Firstpass Theme",
        extensions: ["json"],
    }
}

/**
 * Show a dialog to open a file
 * @returns {Promise<string[]|null>} The selected files
 * @param {Object} opts The options for the dialog
 * @param {string} opts.title The title of the dialog
 * @param {boolean} opts.multi Whether to allow multiple files to be selected
 * @param {boolean} opts.directory Whether to allow selecting a directory
 * @param {string} opts.defaultPath The default path to open the dialog at
 * @param {string} opts.buttonLabel The label of the button
 * @param {Object[]} opts.filters The file filters to use
 */
function openFile({ title="Select a File", multi=false, directory=false, defaultPath, buttonLabel="Select", filters=[] }) {
    const properties = [];
    if (multi) properties.push("multiSelections");
    if (directory) properties.push("openDirectory");
    else properties.push("openFile");

    return ipcRenderer.invoke("showOpenDialog", {
        title,
        defaultPath,
        buttonLabel,
        filters,
        properties,
    });
}

/**
 * Show a dialog to save a file
 * @returns {Promise<string|null>} The selected file
 * @param {Object} opts The options for the dialog
 * @param {string} opts.title The title of the dialog
 * @param {string} opts.defaultPath The default path to open the dialog at
 * @param {string} opts.buttonLabel The label of the button
 * @param {Object[]} opts.filters The file filters to use
 */
function saveFile({ title="Save File", defaultPath, buttonLabel="Save", filters=[] }) {
    return ipcRenderer.invoke("showSaveDialog", {
        title,
        defaultPath,
        buttonLabel,
        filters,
    });
}

let _cached_documents_folder;
/**
 * Get the documents folder
 * @returns {Promise<string|null>} The documents folder
 */
function getDocumentsFolder() {
    if (_cached_documents_folder) return Promise.resolve(_cached_documents_folder);
    return ipcRenderer.invoke("getDocumentsFolder").then(res => {
        _cached_documents_folder = res.replace(/\\/g, "/");
        return _cached_documents_folder;
    });
}

/**
 * Get all saved themes
 * @returns {Promise<Object>} The list of themes
 */
function getThemes() {
    return ipcRenderer.invoke("getThemes");
}


/**
 * Get a theme
 * @param {string} file The name of the theme file
 * @returns {Promise<Object>} The theme
 */
function getTheme(file) {
    return ipcRenderer.invoke("getTheme", file);
}


/**
 * Import a theme
 * @param {string} path The path to the theme file
 * @param {boolean} [overwrite=false] Whether to overwrite an existing theme with the same name
 * @returns {Promise<Object>} The imported theme
 */
function importTheme(path, overwrite=false) {
    return ipcRenderer.invoke("importTheme", path, overwrite);
}


/**
 * Update a theme
 * @param {string} file The name of the theme file
 * @param {Object} theme The updated theme
 */
function updateTheme(file, theme) {
    ipcRenderer.send("updateTheme", file, theme);
}

/**
 * Delete a theme
 * @param {string} file The name of the theme file
 */
function deleteTheme(file) {
    return ipcRenderer.invoke("deleteTheme", file);
}

/**
 * Open a URL in the default browser
 * @param {string} url The URL to open
 */
function openURL(url) {
    url = url.trim();
    if (!url.match(/^https?:\/\//gi)) url = "https://" + url;
    ipcRenderer.send("openURL", url);
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
    openFile, saveFile, FileFilter,
    getDocumentsFolder,
    getThemes, getTheme, importTheme, updateTheme, deleteTheme,
    openURL
};