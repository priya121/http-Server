package main.streams;

public class FakeOutputStream implements StreamWriter {
    public String response;
    public boolean closed = false;

    @Override
    public void write(byte[] message) {
        response += new String(message);
    }

    @Override
    public void close() {
        closed = true;
    }
}
