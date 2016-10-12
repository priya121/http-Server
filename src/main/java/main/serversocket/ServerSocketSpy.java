package main.serversocket;


import main.socket.SocketConnection;

public class ServerSocketSpy implements ServerSocketConnection {
    private final SocketConnection socket;
    public boolean connectionMade = false;
    public int numberOfConnections = 0;

    public ServerSocketSpy(SocketConnection socket) {
        this.socket = socket;
    }

    @Override
    public SocketConnection accept() {
        connectionMade = true;
        numberOfConnections ++;
        return socket;
    }

    @Override
    public void close() {
        socket.close();
    }
}
