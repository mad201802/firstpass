const electron = require("electron");
const { contextBridge, ipcRenderer } = electron;

contextBridge.exposeInMainWorld("electron", {
    ipcRenderer: {
        on: ipcRenderer.on,
        send: ipcRenderer.send,
        invoke: ipcRenderer.invoke,
    }
});