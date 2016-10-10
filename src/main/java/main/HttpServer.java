package main;

import main.serversocket.ServerSocketConnection;
import main.socket.SocketConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.Executors;

public class HttpServer {
    private final ServerSocketConnection serverSocket;

    public HttpServer(ServerSocketConnection serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() {
        SocketConnection socket = serverSocket.accept();
        respondToClient(socket);
        serverSocket.close();
    }

    private void respondToClient(SocketConnection client) {
        Runnable runnable = () -> sendResponse(client);
        Executors.newSingleThreadExecutor().submit(runnable);
    }

    private void sendResponse(SocketConnection socket) {
        OutputStream outputStream = socket.getOutputStream();
        try {
            outputStream.write("HTTP/1.1 200 OK".getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
