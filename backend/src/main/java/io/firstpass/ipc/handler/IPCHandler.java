package io.firstpass.ipc.handler;

import io.firstpass.ipc.handler.interfaces.IHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class is used to handle the input and output streams
 */
public class IPCHandler implements IHandler {

    private final Scanner inputStream;
    private final PrintStream outputStream;

    public IPCHandler(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = new Scanner(inputStream);
        this.outputStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
    }

    /**
     * This method is used to read a line from the input stream
     * @return The line read from the input stream
     */
    public String readLine() {
        if(inputStream.hasNextLine()) {
            return inputStream.nextLine();
        }
        throw new IllegalStateException("No more input to read");
    }

    /**
     * This method is used to write a line to the output stream
     * @param line The line to write to the output stream
     */
    public void writeLine(String line) {
        if(!outputStream.checkError()) {
            outputStream.println(line);
        } else {
            throw new IllegalStateException("Error writing to output stream");
        }
    }

}
