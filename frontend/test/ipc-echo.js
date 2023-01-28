// Test IPC communication between parent and child processes

process.stdin.on('data', (data) => {
    process.stdout.write(data.toString().trim() + " from child\n");
});