const { spawn } = require("child_process");


function timeoutPromise(promise, timeout, message) {
    return Promise.race([
        promise,
        new Promise((_, reject) => setTimeout(() => reject(message), timeout))
    ]);
}


class IPC {

    /** No error occurred */
    static ERR_OK = 0;
    /** An unknown error occurred */
    static ERR_GENERIC = 1;
    /** The process took too long to respond */
    static ERR_TIMEOUT = 2;
     /** Could not spawn the child process */ 
    static ERR_SPAWN = 3;


    _cmd;
    _args;
    _process;
    _onMessage;
    _onError;
    _onExit;


    /**
     * Creates a new IPC instance
     * @param {string} cmd The command to execute
     * @param {string[]} args The arguments to pass to the command
     */
    constructor(cmd, args) {
        this._cmd = cmd;
        this._args = args;

        this._onMessage = () => {};
        this._onError = () => {};
        this._onExit = () => {};
    }

    /**
     * Spawn the child process
     * @returns {Promise} A promise that resolves when the process is connected
     */
    connect() {
        this._process = spawn(this._cmd, this._args);

        this._registerEvents();

        return new Promise((resolve, reject) => {
            const errcb = () => reject(IPC.ERR_SPAWN);
                
            this._process.once("error", errcb);
            this._process.once("spawn", () => {
                resolve();
                this._process.removeListener("error", errcb);
            });
        });
    }

    /**
     * Kill the child process
     * @returns A promise that resolves when the process is killed
     */
    terminate() {
        this._process.kill();

        // return new Promise((resolve, reject) => {
        //     const errcb = () => reject(IPC.ERR_GENERIC, err);
        //     this._process.once("exit", () => {
        //         resolve();
        //         this._process.removeListener("error", errcb);
        //     });
        //     this._process.once("error", errcb);
        // });
    }

    /**
     * Wait for the child process to exit
     * @returns A promise that resolves when the process exits
     */
    awaitTermination() {
        return new Promise((resolve, reject) => {
            const errcb = () => reject(IPC.ERR_GENERIC, err);
            this._process.once("exit", () => {
                resolve();
                this._process.removeListener("error", errcb);
            });
            this._process.once("error", errcb);
        });
    }

    /**
     * Send a message to the child process
     * @param {string} msg The message to send
     */
    send(msg) {
        this._process.stdin.write(msg + "\n");
    }
    
    /**
     * Receive a message from the child process
     * @returns A promise that resolves with the message
     */
    recv(timeout) {
        const promise = new Promise((resolve, reject) => {
            const errcb = () => reject(IPC.ERR_GENERIC);
            this._process.stdout.once("data", (data) => {
                resolve(data.toString().slice(0, -1));
                this._process.removeListener("error", errcb);
            });
            this._process.once("error", errcb);
        });

        return timeout
            ? timeoutPromise(promise, timeout, IPC.ERR_TIMEOUT)
            : promise;
    }

    _registerEvents() {
        this._process.stdout.on("data", (data) => this._onMessage(data.toString()));
        this._process.stderr.on("data", (data) => this._onError(data.toString()));
        this._process.on("close", (code) => this._onExit());
    }
}

module.exports = IPC;