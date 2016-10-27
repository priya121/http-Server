package main.serversocket;

import main.socket.RealSocketConnection;
import main.socket.SocketConnection;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;

public class RealServerSocket implements ServerSocketConnection {
    private final ServerSocket serverSocket;

    public RealServerSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public SocketConnection accept() throws UncheckedIOException {
        try {
            return new RealSocketConnection(serverSocket.accept());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}