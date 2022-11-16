package io.firstpass.ipc.parser;

import java.io.*;
import java.util.Scanner;

public class IPCParser {

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public IPCParser(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void start() throws IOException {
        PrintStream out = new PrintStream(this.outputStream);
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if(line.equals("exit")) break;
                out.println(this.handleMessage(line));
            }
        } catch(Exception ex) {
            out.close();
            ex.printStackTrace();
        }
    }

    private String handleMessage(String message) {
        // Get message type
        return "Hello World!";
    }

}
