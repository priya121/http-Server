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
import java.util.concurrent.ExecutorService;

public class HttpServer {
    private static final boolean SERVER_LISTENING = true;
    private final ServerSocketConnection serverSocket;
    private final String publicDirectory;
    private final ExecutorService executor;

    public HttpServer(ExecutorService executor, ServerSocketConnection serverSocket, String publicDirectory) {
        this.serverSocket = serverSocket;
        this.publicDirectory = publicDirectory;
        this.executor = executor;
    }

    public void start() throws IOException {
        while (SERVER_LISTENING) {
            respondToClient(executor);
        }
    }

    public void respondToClient(ExecutorService executorService) throws IOException {
        SocketConnection socket = serverSocket.accept();
        Runnable runnable = () -> {
            StreamWriter outputStream = createOutputStream(socket);
            BufferedReader inputStream = createInputStream(socket);
            Request request = new Request(inputStream);
            sendResponse(outputStream, request);
            logRequest(request);
        };
        executorService.submit(runnable);
    }

    public void sendResponse(StreamWriter stream, Request request) {
        ActionChooser action = new ActionChooser();
        ResponseFactory factory = new ResponseFactory(publicDirectory);
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

    private BufferedReader createInputStream(SocketConnection socket) {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private StreamWriter createOutputStream(SocketConnection socket) {
        return new RealOutputStreamWriter(socket.getOutputStream());
    }
}
