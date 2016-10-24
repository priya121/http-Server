import main.response.Response;
import org.junit.Test;

import static main.Status.METHOD_NOT_ALLOWED;
import static main.Status.OK;
import static main.Status.PARTIAL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseTest {
        String headers = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                         "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                         "ETag: \n" +
                         "Accept-Ranges: none\n" +
                         "Content-Length: \n" +
                         "Connection: close\n";

    @Test
    public void canGetStatusLine() {
        Response response = new Response(OK.get(),
                                         headers,
                                         "".getBytes());
        assertEquals("HTTP/1.1 200 OK\n", response.getStatusLine());
    }

    @Test
    public void canGetDifferentStatusLine() {
        Response response = new Response(PARTIAL.get(),
                                         headers,
                                         "".getBytes());
        assertEquals("HTTP/1.1 206 Partial\n", response.getStatusLine());
    }

    @Test
    public void canGetCreatedResponse() {
        Response response = new Response(OK.get(),
                                        headers,
                                        "<h1> I'm a teapot </h1>".getBytes());

        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n\n" +
                     "<h1> I'm a teapot </h1>", response.getStatusLine() +
                                                response.getHeader() +
                                                new String(response.getBody()));
    }

    @Test
    public void canGetAnotherCreatedResponse() {
        Response response = new Response(METHOD_NOT_ALLOWED.get(),
                                         headers,
                                         "".getBytes());
        assertEquals("HTTP/1.1 405 Method Not Allowed\n" +
                "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "ETag: \n" +
                "Accept-Ranges: none\n" +
                "Content-Length: \n" +
                "Connection: close\n\n", response.getStatusLine() +
                                                response.getHeader());
    }

    @Test
    public void canCreateAResponseWithDataInBody() {
        byte[] data = "Hi".getBytes();
        Response response = new Response(OK.get(), headers, data);
        assertTrue(response.getBody().length != 0);
    }

    @Test
    public void canCreateAnotherResponseWithDataInBody() {
        byte[] data = "Hi there".getBytes();
        Response response = new Response(OK.get(), headers, data);
        assertTrue(response.getBody().length == 8);
    }
}
