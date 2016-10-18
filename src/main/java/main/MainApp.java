package main;

import main.serversocket.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class MainApp {
    public static void main(String[] args) throws IOException {
        ServerArguments arguments = new ServerArguments(args, System.out);
        int port = arguments.getPort();
        String directory = arguments.getDirectory();
        HttpServer server = new HttpServer(new RealServerSocket(new ServerSocket(port)), directory);
        System.out.print("Starting server...\n");
        server.start();
    }
}
