const electron = require("electron");
const { contextBridge, ipcRenderer } = electron;

async function call(data) {
    const res = await ipcRenderer.invoke("call", data);
    if (res.error) throw res.error;
    return res;
}

let errorHandler = () => {};
ipcRenderer.on("backend-error", (e, data) => errorHandler(data));

contextBridge.exposeInMainWorld("app", {
    minimize: () => ipcRenderer.send("minimize"),
    maximize: () => ipcRenderer.send("maximize"),
    close: () => ipcRenderer.send("close"),

    call,

    onError: (callback) => errorHandler = callback,
});