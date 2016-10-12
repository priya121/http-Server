package main.streams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

public class RealOutputStreamWriter implements StreamWriter {
    private final OutputStream outputStream;

    public RealOutputStreamWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(byte[] message) {
        try {
            outputStream.write(message);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
