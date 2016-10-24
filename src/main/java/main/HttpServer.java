package main;

import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;
import main.serversocket.ServerSocketConnection;
import main.socket.SocketConnection;
import main.streams.RealOutputStreamWriter;
import main.streams.StreamWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final boolean SERVER_LISTENING = true;
    private final ServerSocketConnection serverSocket;
    private final ArrayList content;
    private final String publicDirectory;

    public HttpServer(ServerSocketConnection serverSocket, String publicDirectory) {
        this.serverSocket = serverSocket;
        this.content = new ArrayList();
        this.publicDirectory = publicDirectory;
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
                logRequest(request);
        };
            executeTask(runnable);
    }

    public void sendResponse(StreamWriter stream, Request request) {
        ActionChooser action = new ActionChooser();
        ResponseFactory factory = new ResponseFactory(content, publicDirectory);
        DefaultResponse relevantResponse = factory.findRelevantResponse(request);
        Response response = action.determine(relevantResponse, request);
        writeToClient(stream, response);
    }

    private void logRequest(Request request) {
        Logger logger = new Logger(new File(publicDirectory + "/logs"));
        logger.log(request.getRequestLine());
    }

    private void writeToClient(StreamWriter stream, Response response) {
        stream.write(response.getStatusLine().getBytes());
        stream.write(response.getHeader().getBytes());
        stream.write(response.getBody());
        stream.close();
    }

    private void executeTask(Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(4096);
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
