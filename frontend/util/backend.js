const IPC = require("./ipc");

const { ipcMain, dialog } = require("electron");

const MAX_RESPONSE_TIME = 10000;

let ipc = null;

// Gets called when process exits unexpectedly
let onError = () => {};


function connect() {
    ipc = new IPC("java", ["-jar", "../backend/target/backend-1.0-SNAPSHOT.jar"]);

    ipc._onExit = () => {
        onError({ code: 10, message: "The process exited unexpectedly", id: "ERR_EXIT" });
    };

    ipc.connect().catch(e => {
        onError(e);
    });
}

function registerHandlers() {
    ipcMain.handle("call", (event, data) => {
        return new Promise((resolve, reject) => {
            call(data).then(res => {
                resolve(res);
            }).catch(e => {
                resolve({ error: e });
            });
        });
    });

    ipcMain.handle("showOpenDialog", (e, opts) => {
        return dialog.showOpenDialogSync(opts);
    })
    ipcMain.handle("showSaveDialog", (e, opts) => {
        return dialog.showSaveDialogSync(opts);
    })
}

function restart() {
    disconnect();
    connect();
}

function disconnect() {
    ipc._onExit = () => {};
    call({ type: "SHUTDOWN" });
    return ipc.terminate(false);
}

async function call(data) {
    ipc.send(JSON.stringify(data));
    return ipc.recv(MAX_RESPONSE_TIME);
}


module.exports = {
    connect,
    registerHandlers,
    restart,
    disconnect,
    call,
    onError: (callback) => onError = callback,
}