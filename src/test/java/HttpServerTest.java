import main.HttpServer;
import main.serversocket.RealServerSocket;
import main.serversocket.ServerSocketSpy;
import main.socket.RealSocketConnection;
import main.socket.SocketMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class HttpServerTest {
    private ServerSocketSpy serverSocketConnectionSpy;
    private RealServerSocket serverSocket;
    private RealSocketConnection socket;

    @Before
    public void setUp() throws IOException {
        SocketMock socketMock = new SocketMock();
        serverSocketConnectionSpy = new ServerSocketSpy(socketMock);

        serverSocket = new RealServerSocket(new ServerSocket(4444));
        socket = new RealSocketConnection(new Socket("localhost", 4444));
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
        socket.close();
    }

    @Test
    public void makesConnection() throws IOException {
        HttpServer server = new HttpServer(serverSocketConnectionSpy);
        serverSocketConnectionSpy.connectionMade = false;

        server.start();

        assertTrue(serverSocketConnectionSpy.connectionMade);
    }

    @Test
    public void writesContentToClient() throws IOException {
        HttpServer server = new HttpServer(serverSocket);

        server.start();

        String readMessage = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
        assertThat(readMessage, is("HTTP/1.1 200 OK"));
    }
}
