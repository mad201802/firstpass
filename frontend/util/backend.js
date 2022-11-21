const IPC = require("./ipc");

const { ipcMain } = require("electron");

const MAX_RESPONSE_TIME = 10000;

let ipc = null;

// Gets called when process exits unexpectedly
let onError = () => {};


function connect() {
    ipc = new IPC("jaerva", ["-jar", "../backend/target/backend-1.0.jar"]);

    ipc._onExit = () => {
        onError({ code: 10, message: "The process exited unexpectedly", id: "ERR_EXIT" });
    };

    ipc.connect().catch(e => {
        onError(e);
    });

    ipcMain.handle("call", (event, data) => {
        return new Promise((resolve, reject) => {
            call(data).then(res => {
                resolve(res);
            }).catch(e => {
                resolve({ error: e, a:1 });
            });
        });
    });

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
    call,
    disconnect,
    onError: (callback) => onError = callback,
}