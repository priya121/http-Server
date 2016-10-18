import main.Response;
import org.junit.Test;

import static main.Status.METHOD_NOT_ALLOWED;
import static main.Status.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseTest {
        String headers = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                         "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                         "ETag: \n" +
                         "Accept-Ranges: none\n" +
                         "Content-Length: \n" +
                         "Connection: close\n" +
                         "Content-Type: text/plain\n";

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
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n" +
                     "<h1> I'm a teapot </h1>", response.getHeader() + new String(response.getBody()));
    }

    @Test
    public void canGetAnotherCreatedResponse() {
        Response response = new Response(METHOD_NOT_ALLOWED.get(),
                                         headers,
                                         "");
        assertEquals("HTTP/1.1 405 Method Not Allowed\n" +
                "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "ETag: \n" +
                "Accept-Ranges: none\n" +
                "Content-Length: \n" +
                "Connection: close\n" +
                "Content-Type: text/plain\n\n", response.getHeader());
    }

    @Test
    public void canCreateAResponseWithDataInBody() {
        byte[] data = "Hi".getBytes();
        Response response = new Response(OK.get(), headers, data);
        assertTrue(response.getBody().length != 0);
    }
}
