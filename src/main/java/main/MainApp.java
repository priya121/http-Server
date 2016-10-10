package main;

import main.serversocket.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class MainApp {
    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer(new RealServerSocket(new ServerSocket(5000)));
        System.out.print("Starting server...");
        server.start();
    }
}
