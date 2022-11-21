package io.firstpass.ipc.handler;

import io.firstpass.ipc.handler.interfaces.IHandler;

import java.io.*;
import java.util.Scanner;

public class IPCHandler implements IHandler {

    private final Scanner inputStream;
    private final PrintStream outputStream;

    public IPCHandler(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = new Scanner(inputStream);
        this.outputStream = new PrintStream(outputStream);
    }

    public String readLine() {
        if(inputStream.hasNextLine()) {
            return inputStream.nextLine();
        }
        throw new IllegalStateException("No more input to read");
    }

    public void writeLine(String line) {
        if(!outputStream.checkError()) {
            outputStream.println(line);
        } else {
            throw new IllegalStateException("Error writing to output stream");
        }
    }

}
