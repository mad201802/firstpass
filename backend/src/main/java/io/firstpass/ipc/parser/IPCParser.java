package io.firstpass.ipc.parser;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class IPCParser {

    private final Scanner inputStream;
    private final PrintStream outputStream;

    public IPCParser(InputStream inputStream, OutputStream outputStream) {
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
        }
        throw new IllegalStateException("Error writing to output stream");
    }

}
