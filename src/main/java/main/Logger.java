package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

public class Logger {

    private final File file;

    public Logger(File file) {
        this.file = file;
    }

    public void log(String logText) {
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(logText + "\n");
            writer.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
