package main;

import main.responses.*;
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

    public HttpServer(ServerSocketConnection serverSocket) {
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
        FilePathList fileList = new FilePathList();
        if (request.getPath().equals("/") && request.getRequestMethod().equals("GET")) {
            EmptyPathResponse emptyPath = new EmptyPathResponse(content);
            String response = emptyPath.get(request);
            writeToClient(stream, response);
        } else if (!fileList.validFilePath(request.getPath())) {
            NoResourceResponse noResource = new NoResourceResponse(content);
            String response = noResource.head(request);
            writeToClient(stream, response);
        } else {
            Response response = new Response(content);
            String respond = response.get(request);
            stream.write(respond.getBytes());
            stream.close();
        }
    }

    private void writeToClient(StreamWriter stream, String response) {
        stream.write(response.getBytes());
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
