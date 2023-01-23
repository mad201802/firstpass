const { app } = require("electron");
const path = require("path");
const fs = require("fs");

const { sanitizeName, ensureThemeDir } = require("./util");


const themes = (() => {
    const res = {
        "*default": {
            name: "Default",
            primary: "#f5b302",
            primaryLight: "#fed053",
            primaryDark: "#f5a303",
            bg: "#1f232a",
            bgLight: "#2b2f36", 
            bgLighter: "#3b3f46",
            divider: "#3b3f46",
            text: "#f5f5f5",
            textDark: "#b3b3b3",
            textInv: "#1f232a",
            radius: "5px",
            radiusLg: "10px",
        },
        "*default-light": {
            name: "Default (Light)",
            primary: "#315cdd",
            primaryLight: "#6c90fe",
            primaryDark: "#2249bf",
            bg: "#cecfd4",
            bgLight: "#e3e3e3",
            bgLighter: "#c7c7c7",
            divider: "#d1d1d1",
            text: "#333333",
            textDark: "#383838",
            textInv: "#f2f2f2",
            radius: "5px",
            radiusLg: "10px",
        },
        "*grape": {
            name: "Grape",
            primary: "#a35bec",
            primaryLight: "#c48efb",
            primaryDark: "#8c45d3",
            bg: "#1f232a",
            bgLight: "#2b2f36",
            bgLighter: "#3b3f46",
            divider: "#3b3f46",
            text: "#f5f5f5",
            textDark: "#b3b3b3",
            textInv: "#16191d",
            radius: "5px",
            radiusLg: "10px"
        },
        "*grape-light": {
            name: "Grape (Light)",
            primary: "#5e3687",
            primaryLight: "#7548a3",
            primaryDark: "#4a2b69",
            bg: "#cecfd4",
            bgLight: "#e3e3e3",
            bgLighter: "#c7c7c7",
            divider: "#bababa",
            text: "#333333",
            textDark: "#383838",
            textInv: "#f2f2f2",
            radius: "5px",
            radiusLg: "10px",
        },
        "*forest": {
            name: "Forest",
            primary: "#00bd52",
            primaryLight: "#40ed8b",
            primaryDark: "#16924c",
            bg: "#141619",
            bgLight: "#181b21",
            bgLighter: "#1e2229",
            divider: "#1e2229",
            text: "#bfc9d4",
            textDark: "#757d84",
            textInv: "#141414"
        },
        "*tomato": {
            name: "Tomato",
            primary: "#dd3322",
            primaryLight: "#fe4834",
            primaryDark: "#be2413",
            bg: "#141619",
            bgLight: "#181b21",
            bgLighter: "#1e2229",
            divider: "#1e2229",
            text: "#bfc9d4",
            textDark: "#757d84",
            textInv: "#141414",
            radius: "5px",
            radiusLg: "10px",
        }
    };
    try {
        ensureThemeDir();
        let files = fs.readdirSync(path.join(app.getPath("userData"), "themes"));
        files = files.filter(f => f.endsWith(".json"));

        for (const file of files) {
            try {
                const theme = JSON.parse(fs.readFileSync(path.join(app.getPath("userData"), "themes", file), "utf8"));
                res[file.slice(0, -5)] = theme;
            } catch (e) {
                console.log("Failed to parse theme:", file, e);
            }
        }
    } catch (e) {
        console.log("Failed to load themes:", e);
    }
    return res;
})();

let saveTimeout = null;
let saveQueue = new Set();



// Get all themes from appdata folder
function getThemes() {
    return Object.entries(themes).map(([file, { name }]) => ({ name, file }));
}

function getTheme(file) {
    return themes[file];
}

// Copy a theme to appdata if its valid
function importTheme(filename) {
    const duplication = filename in themes;
    if (!(duplication) && !fs.existsSync(filename)) return null;
    let theme;
    try {
        if (duplication) {
            theme = { ...themes[filename] };
            theme.name += " Copy";
        } else {
            theme = { ...themes["*default"], ...JSON.parse(fs.readFileSync(filename, "utf8")) };
        }
        if (!theme.name) throw new Error(`Theme '${filename}' has no 'name' property`);
        ensureThemeDir();

        let file = sanitizeName(theme.name);
        // const newPath = path.join(app.getPath("userData"), "themes", file);
        for (let n = 0; n < 100; n++) {
            const ext_file = file + (n === 0 ? "" : `_${n}`);
            const newPath = path.join(app.getPath("userData"), "themes", ext_file + ".json");
            if (!fs.existsSync(newPath)) {
                file = ext_file;
                break;
            }
        }
        fs.writeFileSync(
            path.join(app.getPath("userData"), "themes", file + ".json"),
            JSON.stringify(theme, null, 4)
        );

        themes[file] = theme;
        return { file, name: theme.name };
    } catch (e) {
        console.log("Failed to import theme:", e);
        return null;
    }
}


// Update a theme
function updateTheme(file, theme) {
    if (saveTimeout) clearTimeout(saveTimeout);
    themes[file] = { ...themes[file], ...theme };
    saveQueue.add(file);

    saveTimeout = setTimeout(() => {
        saveTimeout = null;
        try {
            for (const file of saveQueue) {
                if (file.startsWith("*")) continue; // Don't save default themes
                ensureThemeDir();
                fs.writeFileSync(
                    path.join(app.getPath("userData"), "themes", file + ".json"),
                    JSON.stringify(themes[file], null, 4)
                );
            }
        } catch (e) {
            console.log("Failed to save theme:", e);
        }
        saveQueue.clear();
    }, 1000);
}

function deleteTheme(file) {
    if (file.startsWith("*") || !file in themes) return false;
    delete themes[file];
    try {
        fs.unlinkSync(path.join(app.getPath("userData"), "themes", file + ".json"));
    } catch (e) {
        console.log("Failed to delete theme:", e);
    }
    return true;
}


module.exports = {
    getThemes,
    getTheme,
    updateTheme,
    importTheme,
    deleteTheme
};