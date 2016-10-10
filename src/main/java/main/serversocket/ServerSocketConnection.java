package main.serversocket;

import main.socket.SocketConnection;

public interface ServerSocketConnection {
    SocketConnection accept();
    void close();
}
