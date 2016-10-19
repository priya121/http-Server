import main.Protocol;
import main.request.RequestLine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestLineTest {
    private final Protocol protocol = new Protocol("HTTP 1.1");

    @Test
    public void hasAMethodType() {
        RequestLine requestLine = new RequestLine("GET", "/foobar", protocol);
        assertEquals("GET", requestLine.getMethodType());
    }

    @Test
    public void hasADifferentMethodType() {
        RequestLine requestLine = new RequestLine("PUT", "/form", protocol);
        assertEquals("PUT", requestLine.getMethodType());
    }

    @Test
    public void getsPath() {
        RequestLine requestLine = new RequestLine("PUT", "/file1", protocol);
        assertEquals("/file1", requestLine.getPath());
    }

    @Test
    public void getsAnotherPath() {
        RequestLine requestLine = new RequestLine("PUT", "/text-file.txt", protocol);
        assertEquals("/text-file.txt", requestLine.getPath());
    }

    @Test
    public void getsProtocol() {
        RequestLine requestLine = new RequestLine("GET", "/", protocol);
        assertEquals("HTTP 1.1", requestLine.getProtocol());
    }
}
