// Test IPC communication between parent and child processes

process.stdin.on('data', (data) => {
    const msg = data.toString().trim();
    switch (msg) {
        case "simulate crash":
            throw new Error("Simulated crash");

        default:            
            process.stdout.write(msg + " from child\n");
            break;
    }
});