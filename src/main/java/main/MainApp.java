package main;

import main.serversocket.RealServerSocket;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {
    public static void main(String[] args) throws IOException {
        ServerArguments arguments = new ServerArguments(args, System.out);
        int port = arguments.getPort();
        String directory = arguments.getDirectory();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        HttpServer server = new HttpServer(executorService, new RealServerSocket(port), directory);
        System.out.print("Starting server...\n");
        server.start();
    }
}
