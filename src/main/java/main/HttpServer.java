package main;

import main.responses.DefaultResponse;
import main.serversocket.ServerSocketConnection;
import main.socket.SocketConnection;
import main.streams.RealOutputStreamWriter;
import main.streams.StreamWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final boolean SERVER_LISTENING = true;
    private final ServerSocketConnection serverSocket;
    private final ArrayList content;

    public HttpServer(ServerSocketConnection serverSocket, String publicDirectory) {
        this.serverSocket = serverSocket;
        this.content = new ArrayList();
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
        ActionChooser action = new ActionChooser();
        ResponseFactory factory = new ResponseFactory(content);
        DefaultResponse relevantResponse = factory.findRelevantResponse(request);
        Response response = action.determine(relevantResponse, request);
        writeToClient(stream, response);
    }

    private void writeToClient(StreamWriter stream, Response response) {
        stream.write(response.getHeader().getBytes());
        stream.write(response.getBody());
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
