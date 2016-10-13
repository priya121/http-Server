import main.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class RequestTest {
    private String getRequest;
    private BufferedReader reader;
    private String getFooRequest;

    @Before
    public void setUp() {
        getRequest = "GET / HTTP/1.1\n" +
                           "Host: localhost:5000\n" +
                           "Connection: Keep-Alive\n" +
                           "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                           "Accept-Encoding: gzip,deflate";
        reader = createBufferedReader(getRequest);
    }

    @Test
    public void findsRequestLine() throws IOException {
        Request request = new Request(reader);
        assertEquals("GET / HTTP/1.1", request.getRequestLine());
    }

    @Test
    public void getsHost() throws IOException {
        Request request = new Request(reader);
        assertEquals("localhost:5000" , request.getHost());
    }

    @Test
    public void getsConnection() throws IOException {
        Request request = new Request(reader);
        assertEquals("Keep-Alive" , request.getConnection());
    }

    @Test
    public void noPathFound() {
        reader = createBufferedReader(getRequest);
        Request request = new Request(reader);
        assertEquals("No path" , request.getPath());
    }

    @Test
    public void getsPathIfPresent() {
        getFooRequest = "GET /foobar HTTP/1.1\n" +
                        "Host: localhost:5000\n" +
                        "Connection: Keep-Alive\n" +
                        "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                        "Accept-Encoding: gzip,deflate";
        reader = createBufferedReader(getFooRequest);
        Request request = new Request(reader);
        assertEquals("/foobar" , request.getPath());
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
