package main;

import main.serversocket.ServerSocketConnection;
import main.socket.SocketConnection;
import main.streams.*;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final boolean SERVER_LISTENING = true;
    private final ServerSocketConnection serverSocket;

    public HttpServer(ServerSocketConnection serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() throws IOException {
        while (SERVER_LISTENING) {
            respondToClient();
        }
    }

    public void respondToClient() throws IOException {
        SocketConnection socket = serverSocket.accept();

        StreamWriter outputStream = createOutputStream(socket);
        BufferedReader inputStream = createInputStream(socket);

        Request request = new Request(inputStream);

        Runnable runnable = () -> {
                sendResponse(outputStream, request);
        };
            executeTask(runnable);
    }

    public void sendResponse(StreamWriter stream, Request request) {
        Response response = new Response();
        String respond = response.get(request);
        stream.write(respond.getBytes());
        stream.close();
    }

    private void executeTask(Runnable runnable) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(runnable);
        executorService.shutdown();
    }

    private BufferedReader createInputStream(SocketConnection socket) {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private StreamWriter createOutputStream(SocketConnection socket) {
        return new RealOutputStreamWriter(socket.getOutputStream());
    }
}
