const electron = require("electron");
const { ipcMain } = require("electron/main");
const path = require("path");

const backend = require("./util/backend");

const { app, BrowserWindow, Menu, nativeTheme } = electron;
nativeTheme.themeSource = "dark";

let mainWindow;

app.on("ready", () => {

    // Disable Shortcuts (prevent reloading)
    if (app.isPackaged)
        Menu.setApplicationMenu(null);

    mainWindow = new BrowserWindow({
        webPreferences: {
            nodeIntegration: false,
            contextIsolation: true,
            preload: path.join(__dirname, "/preload.js"),
            devTools: !app.isPackaged
        },

        frame: false,
        backgroundColor: "#00000000",

        show: false,

        width: 900,
        height: 600,
        minHeight: 500,
        minWidth: 700,

        icon: "firstpass.ico",
        title: "Firstpass"
    });

    mainWindow.loadURL(`file://${__dirname}/app/build/index.html`).then(() => {
        mainWindow.show();
        if (!app.isPackaged) mainWindow.webContents.openDevTools({ mode: "detach" });

        backend.onError(e => {
            console.log("sending error", e);
            mainWindow.webContents.send("backend-error", e);
        });
        backend.connect();
        backend.registerHandlers();
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
