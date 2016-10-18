package main;

import main.serversocket.RealServerSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class MainApp {
    public static void main(String[] args) throws IOException {
        String publicDirectory = "/Users/priyapatil/cob_spec/public";
        HttpServer server = new HttpServer(new RealServerSocket(new ServerSocket(5000)), publicDirectory);
        System.out.print("Starting server...\n");
        server.start();
    }
}
