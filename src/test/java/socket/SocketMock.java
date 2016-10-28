package socket;

import main.socket.SocketConnection;

import java.io.*;

public class SocketMock implements SocketConnection {
    public boolean closed = false;
    public final String messages = "GET / HTTP/1.1\n" +
                                   "Host: localhost:5000\n" +
                                   "Connection: Keep-Alive\n" +
                                   "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                                   "Accept-Encoding: gzip,deflate";

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(messages.getBytes());
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
