const { spawn } = require("child_process");


function timeoutPromise(promise, timeout, message) {
    return Promise.race([
        promise,
        new Promise((_, reject) => setTimeout(() => reject(message), timeout))
    ]);
}


class IPC {

    static ERR_OK = { code: 0, message: "No error occurred", id: "ERR_OK" };
    static ERR_GENERIC = { code: 1, message: "An unknown error occurred", id: "ERR_GENERIC" };
    static ERR_TIMEOUT = { code: 2, message: "The process took too long to respond", id: "ERR_TIMEOUT" };
    static ERR_SPAWN = { code: 3, message: "Could not spawn the child process", id: "ERR_SPAWN" };


    $cmd;
    $args;

    process;

    _onMessage;
    _onError;
    _onExit;


    /**
     * Creates a new IPC instance
     * @param {string} cmd The command to execute
     * @param {string[]} args The arguments to pass to the command
     */
    constructor(cmd, args) {
        this.$cmd = cmd;
        this.$args = args;

        this._onMessage = () => {};
        this._onError = () => {};
        this._onExit = () => {};
    }

    /**
     * Spawn the child process
     * @returns {Promise} A promise that resolves when the process is spawned
     */
    connect() {
        this.process = spawn(this.$cmd, this.$args);

        this._registerEvents();

        return new Promise((resolve, reject) => {
            const errcb = () => reject(IPC.ERR_SPAWN);
                
            this.process.once("error", errcb);
            this.process.once("spawn", () => {
                resolve();
                this.process.removeListener("error", errcb);
            });
        });
    }

    /**
     * Wait for the child process to terminate
     * @param {boolean} kill Whether to send a kill signal (default: true)
     */
    terminate(kill = true) {
        if (kill) this.process.kill();

        if (!this.$terminationPromise) {
            this.$terminationPromise = new Promise((resolve, reject) => {
                const errcb = () => reject(IPC.ERR_GENERIC, err);
                this.process.once("exit", () => {
                    resolve();
                    this.process.removeListener("error", errcb);
                });
                this.process.once("error", errcb);
            });
        }
        
        return this.$terminationPromise;
    }

    /**
     * Send a message to the child process
     * @param {string} msg The message to send
     */
    send(msg) {
        this.process.stdin.write(msg + "\n");
    }
    
    /**
     * Receive a message from the child process
     * @returns A promise that resolves with the message
     */
    recv(timeout) {
        const promise = new Promise((resolve, reject) => {
            const errcb = () => reject(IPC.ERR_GENERIC);
            this.process.stdout.once("data", (data) => {
                resolve(data.toString().slice(0, -1));
                this.process.removeListener("error", errcb);
            });
            this.process.once("error", errcb);
        });

        return timeout
            ? timeoutPromise(promise, timeout, IPC.ERR_TIMEOUT)
            : promise;
    }

    _registerEvents() {
        this.process.stdout.on("data", (data) => this._onMessage(data.toString()));
        this.process.stderr.on("data", (data) => this._onError(data.toString()));
        this.process.on("exit", (code) => {
            this.exitCode = code;
            this._onExit(code);
        });
    }
}

module.exports = IPC;