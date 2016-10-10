package main.socket;

import java.io.InputStream;
import java.io.OutputStream;

public interface SocketConnection {
    InputStream getInputStream();
    OutputStream getOutputStream();
    void close();
}
