import main.request.Request;
import org.junit.Before;
import org.junit.Test;
import response.TestHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public class RequestTest {
    private BufferedReader reader;
    private String simpleGetRequest;
    private String getRequestWithFile1Path;
    private String getRequestWithFoobarPath;
    private String getPartialRequest;
    private Request getPatchRequest;
    TestHelper helper = new TestHelper();

    @Before
    public void setUp() {
        simpleGetRequest = createRequestString("/");
        getRequestWithFile1Path = createRequestString("/file1");
        getRequestWithFoobarPath = createRequestString("/foobar");
        getPartialRequest = createGetPartialRequest("/partial_content.txt");
        getPatchRequest = helper.requestWithEtag("GET /patch-context.txt", "e0023aa4e");
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
        assertEquals(4, request.getHeaders().size());
    }

    @Test
    public void getsDifferentHeaderFields() {
        String differentGetRequest = requestWithDifferentUserAgent("/");
        BufferedReader reader = createBufferedReader(differentGetRequest);
        Request request = new Request(reader);
        assertEquals("Apache-HttpClient/4.3.5 (Win32)", request.getHeaders().get("User-Agent"));
    }

    @Test
    public void storesRangeForARequestWithARange() {
        reader = createBufferedReader(getPartialRequest);
        Request request = new Request(reader);
        assertThat(request.getHeaders().get("Range"), is("bytes=0-4"));
    }

    @Test
    public void canGetBytesFromHeader() {
        reader = createBufferedReader(getPartialRequest);
        Request request = new Request(reader);
        assertThat(request.getHeaders().get("Range"), is("bytes=0-4"));
    }

    @Test
    public void findsEtagIfPresent() {
        Request request = getPatchRequest;
        assertThat(request.getHeaders().get("If-Match"), is("e0023aa4e"));
    }

    @Test
    public void getsContentLength() {
        Request request = getPatchRequest;
        assertThat(request.getHeaders().get("Content-Length"), containsString("15"));
    }

    @Test
    public void getsBody() {
        Request request = getPatchRequest;
        assertThat(request.getBody(), is("patched content"));
    }

    @Test
    public void getsEtag() {
        Request request = getPatchRequest;
        assertThat(request.getHeaders().get("If-Match"), containsString("e0023aa4e"));
    }

    private String createRequestString(String path) {
        return  "GET "+ path + " HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
    }

    private String createGetPartialRequest(String path) {
        return  "GET "+ path + " HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Range: bytes=0-4\n" +
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
