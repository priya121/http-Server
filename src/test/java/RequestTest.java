import main.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public class RequestTest {
    private BufferedReader reader;
    private String simpleGetRequest;
    private String getRequestWithFile1Path;
    private String getRequestWithFoobarPath;

    @Before
    public void setUp() {
        simpleGetRequest = createRequestString("/");
        getRequestWithFile1Path = createRequestString("/file1");
        getRequestWithFoobarPath = createRequestString("/foobar");
    }

    @Test
    public void storesRequestLine() {
        reader = createBufferedReader(simpleGetRequest);
        Request request = new Request(reader);
        assertThat(request.getRequestLine(), is("GET / HTTP/1.1"));
    }

    @Test
    public void pathForFoobar() {
        reader = createBufferedReader(getRequestWithFoobarPath);
        Request request = new Request(reader);
        assertEquals("/foobar", request.getPath());
    }

    @Test
    public void pathForFile1() {
        reader = createBufferedReader(getRequestWithFile1Path);
        Request request = new Request(reader);
        assertEquals("/file1", request.getPath());
    }

    @Test
    public void emptyPath() {
        reader = createBufferedReader(simpleGetRequest);
        Request request = new Request(reader);
        assertEquals("/", request.getPath());
    }

    @Test
    public void getsHeaderFields() {
        reader = createBufferedReader(simpleGetRequest);
        Request request = new Request(reader);
        assertEquals("Connection: Keep-Alive\n" +
                     "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "Host: localhost:5000\n" +
                     "Accept-Encoding: gzip,deflate\n", request.getHeaderFields());
    }

    @Test
    public void getsDifferentHeaderFields() {
        String differentGetRequest = requestWithDifferentUserAgent("/");
        BufferedReader reader = createBufferedReader(differentGetRequest);
        Request request = new Request(reader);
        assertEquals("Connection: Keep-Alive\n" +
                     "User-Agent: Apache-HttpClient/4.3.5 (Win32)\n" +
                     "Host: localhost:5000\n" +
                     "Accept-Encoding: gzip,deflate\n", request.getHeaderFields());
    }

    private String createRequestString(String path) {
        return  "GET "+ path + " HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
    }

    private String requestWithDifferentUserAgent(String path) {
        return "GET " + path + " HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (Win32)\n" +
                "Accept-Encoding: gzip,deflate\n";
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
