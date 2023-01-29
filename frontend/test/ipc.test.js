const IPC = require("../util/ipc");

describe("IPC", () => {
    let ipc;

    beforeEach(() => {
        ipc = new IPC("node", ["test/ipc-echo.js"]);
    });

    afterEach(() => {
        if (ipc.process && !ipc.process.exitCode) {
            ipc.process.kill();
        }
    });

    describe("constructor", () => {
        it("should set cmd and args correctly", () => {
            expect(ipc.$cmd).toEqual("node");
            expect(ipc.$args).toEqual(["test/ipc-echo.js"]);
        });
    });

    describe("connect", () => {
        it("should spawn the child process", async () => {
            await ipc.connect();
            expect(ipc.process).toBeDefined();
            expect(ipc.process.pid).toBeDefined();
            expect(ipc.process.exitCode).toBe(null);
        });

        it("should reject with ERR_SPAWN if an error occurs", async () => {
            ipc = new IPC("nonexistent", ["arg1"]);
            const promise = ipc.connect();
            await expect(promise).rejects.toEqual(IPC.ERR_SPAWN);
        });
    });

    describe("terminate", () => {
        it("should kill the process if kill is true", async () => {
            await ipc.connect();
            await ipc.terminate();
            expect(ipc.process.exitCode).toBeDefined();
        });

        it("should not kill the process if kill is false", async () => {
            await ipc.connect();
            const promise = ipc.terminate(false);
            // Wait some time then make sure the process didnt exit yet, then call kill() manually and make sure it exits
            await new Promise(resolve => setTimeout(resolve, 100));
            expect(ipc.process.exitCode).toBe(null);
            ipc.process.kill();
            await promise;
            expect(ipc.process.exitCode).toBeDefined();
        });

        it("should return a promise that resolves when the process exits", async () => {
            // Make sure the promise resolves after the process exits
            await ipc.connect();
            await ipc.terminate();
            expect(ipc.process.exitCode).toBeDefined();
        });

        it("should return a promise that rejects if an error occurs", async () => {
            await ipc.connect();
            const promise = ipc.terminate();
            ipc.process.emit("error", "test error");
            expect(promise).rejects.toEqual(IPC.ERR_GENERIC);
        });
    });

    describe("send", () => {
        it("should write to stdin", async () => {
            await ipc.connect();
            ipc.send("hello");
            expect(ipc.process.stdin.writableLength).toBe(6); // 6 bytes for "hello\n"
        });
    });

    describe("recv", () => {
        it("should resolve with the message", async () => {
            ipc.connect();
            ipc.send("Hello World");
            const data = await ipc.recv();
            expect(data).toBe("Hello World from child");
        });

        it("should reject with ERR_TIMEOUT if the process takes too long to respond", async () => {
            ipc.connect();
            ipc.send("Hello World");
            const promise = ipc.recv(1);
            await expect(promise).rejects.toEqual(IPC.ERR_TIMEOUT);
        });

        it("should reject with ERR_GENERIC if process crashes", async () => {
            await ipc.connect();
            ipc.send("simulate crash");
            const promise = ipc.recv();
            await expect(promise).rejects.toEqual(IPC.ERR_GENERIC);
        });
    });

    describe("encoding", () => {
        it("should use utf8 encoding by default", async () => {
            await ipc.connect();
            const msg = "UTF8 Chars: äöüß😂🤣✔🤷‍♂️✌";
            ipc.send(msg);
            const data = await ipc.recv();
            expect(data).toBe(msg + " from child");
        });
    });
});
