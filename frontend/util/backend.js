const IPC = require("./ipc");

let ipc = null;
let _handlers = {
    "error": [],
    "exit": [],
}

function connect() {
    ipc = new IPC("java", ["-jar", "../backend/target/backend-1.0.jar"]);
    
    ipc.onError = (err) => {
        _dispatch("error", err);
    };
    ipc.onExit = (code) => {
        _dispatch("exit", code);
    };

    ipc.connect().catch(code => _dispatch("error", code));
}

async function disconnect() {
    ipc.terminate();
    return ipc.awaitTermination();
}

async function call(data) {
    ipc.send(JSON.stringify(data));
    return ipc.recv(1000);
}


// Event Handling
function _dispatch(event, data) {
    if (!(event in _handlers)) throw new Error("Invalid event");
    _handlers[event].forEach((handler) => handler(data));
}
function on(event, handler) {
    if (!(event in _handlers)) throw new Error("Invalid event");
    _handlers[event].push(handler);
}
function off(event, handler) {
    if (!(event in _handlers)) throw new Error("Invalid event");
    _handlers[event] = _handlers[event].filter((h) => h !== handler);
}


module.exports = {
    on, off,
    connect,
    call,
    disconnect
}