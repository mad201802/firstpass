const electron = require("electron");
const { ipcMain } = require("electron/main");

const backend = require("./util/backend");

const { app, BrowserWindow } = electron;

let mainWindow;

app.on("ready", () => {
    mainWindow = new BrowserWindow({
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
        },

        frame: false,
        backgroundColor: "#00000000",

        show: false,

        width: 900,
        height: 600,
        minHeight: 500,
        minWidth: 700,

        icon: "firstpass.ico"
    });

    mainWindow.loadURL(`file://${__dirname}/app/build/index.html`).then(() => {
        mainWindow.show();
        if (!app.isPackaged) mainWindow.webContents.openDevTools({ mode: "detach" });

        backend.onError(e => {
            console.log("sending error", e);
            mainWindow.webContents.send("backend-error", e);
        });
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
        app.quit();
    });
});
