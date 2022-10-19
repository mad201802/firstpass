const electron = require("electron");
const { ipcMain } = require("electron/main");
const { ipcRenderer } = require("electron/renderer");
const path = require("path");

const { app, BrowserWindow } = electron;

let mainWindow;

app.on("ready", () => {
    mainWindow = new BrowserWindow({
        
        webPreferences: {
            nodeIntegration: false,
            contextIsolation: true,
            preload: path.join(__dirname, "/preload.js"),
            // devTools: false,
        },

        frame: false,
        transparent: true,
        backgroundColor: "#00000000",

        show: false,

        width: 900,
        height: 600,
    });
    mainWindow.loadURL(`file://${__dirname}/app/build/index.html`).then(() => {
        mainWindow.show();
        mainWindow.webContents.openDevTools({ mode: "detach" });
    });

    ipcMain.on("minimize", () => {
        mainWindow.minimize();
    });
    ipcMain.on("maximize", () => {
        if (mainWindow.isMaximized()) {
            mainWindow.unmaximize();
        } else {
            mainWindow.maximize();
        }
    });
    ipcMain.on("close", () => {
        mainWindow.close();
    });

});