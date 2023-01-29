const IPC = require("./ipc");

const { ipcMain, dialog, app } = require("electron");

const MAX_RESPONSE_TIME = 10000;

let ipc = null;

// Gets called when process exits unexpectedly
let onError = () => {};


function connect() {
    const jarLocation = app.isPackaged ? "./backend.jar" : `../backend/target/backend-${process.env.npm_package_version}.jar`;
    ipc = new IPC("java", ["-jar", jarLocation, "--no-logging"]);

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
}

function restart() {
    disconnect();
    connect();
}

async function disconnect() {
    ipc._onExit = () => {};
    await call({ type: "CLOSE_DB", data: {} });
    return ipc.terminate(false);
}

async function call(data) {
    ipc.send(JSON.stringify(data));
    return ipc.recv(MAX_RESPONSE_TIME);
}

let attempts = 0;
module.exports = {
    connect,
    registerHandlers,
    restart,
    disconnect,
    call,
    onError: (callback) => onError = callback,
    attempts
}