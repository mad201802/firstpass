const electron = require("electron");
const { contextBridge, ipcRenderer } = electron;

contextBridge.exposeInMainWorld("electron", {
    ipcRenderer: {
        on: (...args) => ipcRenderer.on(...args),
        send: ipcRenderer.send,
        invoke: ipcRenderer.invoke,
    }
});