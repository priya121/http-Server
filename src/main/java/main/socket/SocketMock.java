package main.socket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SocketMock implements SocketConnection {
    public boolean closed = false;

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }

    @Override
    public void close() {
       closed = true;
    }
}
