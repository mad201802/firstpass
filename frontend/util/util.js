const { app } = require("electron");
const path = require("path");
const fs = require("fs");

function sanitizeName(name) {
    return name.toLowerCase().trim()
        .replace(/[\s_-]+/g, "_")
        .replace(/\W/g, "");
}

function ensureThemeDir() {
    const dir = path.join(app.getPath("userData"), "themes");
    try {
        if (!fs.existsSync(dir)) fs.mkdirSync(dir);
    } catch (e) {
        console.log("Failed to create themes directory:", e);
    }
}

module.exports = {
    sanitizeName,
    ensureThemeDir
}