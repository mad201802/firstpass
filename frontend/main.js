const electron = require("electron");
const { ipcMain } = require("electron/main");

const path = require("path");
const backend = require("./util/backend");

const { app, BrowserWindow } = electron;

let mainWindow;

app.on("ready", () => {

    mainWindow = new BrowserWindow({
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            // preload: path.join(__dirname, "/preload.js"),
        },

        frame: false,
        backgroundColor: "#00000000",

        show: false,

        width: 900,
        height: 600,
    });
    
    mainWindow.loadURL(`file://${__dirname}/app/build/index.html`).then(() => {
        mainWindow.show();
        mainWindow.webContents.openDevTools({ mode: "detach" });

        backend.onError((e) => {
            console.log("sending error", e);
            mainWindow.webContents.send("backend-error", e);
        });
        console.log("connecting");
        backend.connect();
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