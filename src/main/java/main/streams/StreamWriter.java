package main.streams;

public interface StreamWriter {
    void write(byte[] message);
    void close();
}
