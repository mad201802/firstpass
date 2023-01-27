package io.firstpass.ipc.handler.interfaces;

public interface IHandler {
    /**
     * This method is used to read a line from the input stream
     * @return The line read from the input stream
     */
    String readLine();

    /**
     * This method is used to write a line to the output stream
     * @param line The line to write to the output stream
     */
    void writeLine(String line);
}
