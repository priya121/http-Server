import main.HttpServer;
import main.Request;
import main.serversocket.RealServerSocket;
import main.serversocket.ServerSocketSpy;
import main.socket.RealSocketConnection;
import main.socket.SocketMock;
import main.streams.FakeOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class HttpServerTest {
    private ServerSocketSpy serverSocketConnectionSpy;
    private RealServerSocket realServerSocket;
    private SocketMock socketMock;
    private RealSocketConnection socket;
    private String getRequest;
    private String bogusRequest;

    @Before
    public void setUp() throws IOException {
        socketMock = new SocketMock();
        serverSocketConnectionSpy = new ServerSocketSpy(socketMock);
        realServerSocket = new RealServerSocket(new ServerSocket(4444));
        socket = new RealSocketConnection(new Socket("localhost", 4444));
        getRequest = "GET / HTTP/1.1\n" +
                     "Host: localhost:5000\n" +
                     "Connection: Keep-Alive\n" +
                     "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "Accept-Encoding: gzip,deflate";
        bogusRequest = "BOGUS / HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
    }

    @After
    public void tearDown() throws IOException {
        socket.close();
        realServerSocket.close();
    }

    @Test
    public void makesConnection() throws IOException {
        HttpServer server = new HttpServer(serverSocketConnectionSpy);
        serverSocketConnectionSpy.connectionMade = false;

        server.respondToClient();

        assertTrue(serverSocketConnectionSpy.connectionMade);
    }

    @Test
    public void writesContentToClient() throws IOException {
        HttpServer server = new HttpServer(serverSocketConnectionSpy);
        FakeOutputStream stream = new FakeOutputStream();
        BufferedReader reader = createBufferedReader(getRequest);
        Request request = new Request(reader);

        server.sendResponse(stream, request);

        assertThat(stream.response, containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void writesDifferentContentToClient() throws IOException {
        HttpServer server = new HttpServer(serverSocketConnectionSpy);
        FakeOutputStream stream = new FakeOutputStream();
        BufferedReader reader = createBufferedReader(bogusRequest);
        Request request = new Request(reader);

        server.sendResponse(stream, request);

        assertThat(stream.response, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void socketClosedAfterResponseSent() {
        HttpServer server = new HttpServer(serverSocketConnectionSpy);
        FakeOutputStream stream = new FakeOutputStream();
        stream.closed = false;
        BufferedReader reader = createBufferedReader(getRequest);
        Request request = new Request(reader);

        server.sendResponse(stream, request);

        assertTrue(stream.closed);
    }

    @Test
    public void canAcceptTwoClients() throws IOException {
        serverSocketConnectionSpy = new ServerSocketSpy(socketMock);
        HttpServer server = new HttpServer(serverSocketConnectionSpy);

        server.respondToClient();
        server.respondToClient();

        assertEquals(2, serverSocketConnectionSpy.numberOfConnections);
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
