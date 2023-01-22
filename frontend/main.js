const electron = require("electron");
const { ipcMain } = require("electron/main");
const path = require("path");

const backend = require("./util/backend");
const themes = require("./util/themes");

const { app, BrowserWindow, Menu, nativeTheme, dialog, shell } = electron;
nativeTheme.themeSource = "dark";

// If another instance is running, quit this one
const aquired_lock = app.requestSingleInstanceLock();
if (!aquired_lock) app.quit();


let mainWindow;

app.on("ready", () => {

    // When user tries to launch another instance, focus the main window
    app.on("second-instance", (e, argv, cwd) => {
        if (mainWindow) {
            if (mainWindow.isMinimized()) mainWindow.restore();
            mainWindow.focus();
        }
    });

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

    mainWindow.webContents.executeJavaScript(`window.isPackaged = ${app.isPackaged};`);

    mainWindow.loadURL(`file://${__dirname}/app/build/index.html`).then(() => {
        mainWindow.show();
        if (!app.isPackaged) mainWindow.webContents.openDevTools({ mode: "detach" });

        backend.onError(e => {
            console.log("Backend crashed", e);
            mainWindow.webContents.send("backend-error", e);
            if (backend.attempts < 10) {
                setTimeout(backend.connect, backend.attempts * 100);
            } else {
                mainWindow.webContents.send("backend-error", {...e, fatal: true });
                console.log("Backend crashed too many times, giving up");
            }
            ++backend.attempts;
        });
        backend.connect();
        backend.registerHandlers();
    });


    // Event Handlers
    ipcMain.handle("getDocumentsFolder", () => {
        return app.getPath("documents");
    });

    ipcMain.handle("showOpenDialog", (e, opts) => {
        return dialog.showOpenDialogSync(opts);
    });
    ipcMain.handle("showSaveDialog", (e, opts) => {
        return dialog.showSaveDialogSync(opts);
    });
    ipcMain.on("openURL", (e, url) => {
        shell.openExternal(url);
    });

    // Themes
    ipcMain.handle("getThemes", (e) => themes.getThemes());
    ipcMain.handle("getTheme", (e, ...args) => themes.getTheme(...args));
    ipcMain.handle("importTheme", (e, ...args) => themes.importTheme(...args));
    ipcMain.on("updateTheme", (e, ...args) => themes.updateTheme(...args));
    ipcMain.handle("deleteTheme", (e, ...args) => themes.deleteTheme(...args));

    // Window Events
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