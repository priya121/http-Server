import main.HttpServer;
import main.request.Request;
import main.serversocket.RealServerSocket;
import main.socket.RealSocketConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import socket.SocketMock;
import spies.ExecutorSpy;
import serversocket.ServerSocketSpy;
import streams.OutputStreamSpyMock;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpServerTest {
    private ServerSocketSpy serverSocketConnectionSpy;
    private RealServerSocket realServerSocket;
    private SocketMock socketMock;
    private RealSocketConnection socket;
    private String getRequest;
    private String bogusRequest;
    private String publicDirectory;
    private ExecutorSpy spy;

    @Before
    public void setUp() throws IOException {
        publicDirectory = "/Users/priyapatil/cob_spec/public";
        socketMock = new SocketMock();
        spy = new ExecutorSpy();
        serverSocketConnectionSpy = new ServerSocketSpy(socketMock);
        realServerSocket = new RealServerSocket(4444);
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
        serverSocketConnectionSpy.connectionMade = false;

        server().respondToClient(spy);

        assertTrue(serverSocketConnectionSpy.connectionMade);
    }

    @Test
    public void writesContentToClient() throws IOException {
        OutputStreamSpyMock stream = new OutputStreamSpyMock();

        server().sendResponse(stream, sampleRequest());

        assertThat(stream.response, containsString("HTTP/1.1 200 OK"));
    }

    @Test
    public void writesDifferentContentToClient() throws IOException {
        OutputStreamSpyMock stream = new OutputStreamSpyMock();
        BufferedReader reader = createBufferedReader(bogusRequest);

        server().sendResponse(stream, new Request(reader));

        assertThat(stream.response, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void socketClosedAfterResponseSent() {
        OutputStreamSpyMock stream = new OutputStreamSpyMock();
        stream.closed = false;

        server().sendResponse(stream, sampleRequest());

        assertTrue(stream.closed);
    }

    @Test
    public void canAcceptTwoClients() throws IOException {
        ExecutorSpy spy = new ExecutorSpy();
        serverSocketConnectionSpy = new ServerSocketSpy(socketMock);

        server().respondToClient(spy);
        server().respondToClient(spy);

        assertEquals(2, serverSocketConnectionSpy.numberOfConnections);
    }

    @Test
    public void createsMultipleThreads() throws IOException {
        ExecutorSpy spy = new ExecutorSpy();
        serverSocketConnectionSpy = new ServerSocketSpy(socketMock);

        server().respondToClient(spy);
        server().respondToClient(spy);
        server().respondToClient(spy);

        assertEquals(3, spy.timesCalled());
    }

    private Request sampleRequest() {
        BufferedReader reader = createBufferedReader(getRequest);
        return new Request(reader);
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private HttpServer server() {
        return new HttpServer(spy, serverSocketConnectionSpy, publicDirectory);
    }
}
