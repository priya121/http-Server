import main.Request;
import main.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class ResponseTest {
    private String statusLine;
    private String responseHeader;
    String getRequestContent = "GET /method_options HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate";
    String getCoffee = "GET /coffee HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate";
    String putRequestContents = "PUT /method_options HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate";
    String postRequestContents = "POST /form HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate";
    private BufferedReader reader;
    private Request putRequest;
    private Request postRequest;

    @Before
    public void setUp() {
        statusLine = "HTTP/1.1 200 OK";
        responseHeader = "Date: Mon, 27 Jul 2009 12:28:53 GMT\n" +
                         "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                         "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n" +
                         "Content-Length: 11\n" +
                         "Content-Type: text/html\n" +
                         "Connection: Closed";
        reader = createBufferedReader(getRequestContent);
        putRequest = new Request(reader);
    }

    @Test
    public void sends200OKtoGETRequest() {
        Response response = new Response();
        String responseCode = response.determineStatusLine(putRequest);
        assertEquals("HTTP/1.1 200 OK\n", responseCode);
    }

    @Test
    public void sends200OKforPUTRequest() {
        reader = createBufferedReader(putRequestContents);
        putRequest = new Request(reader);
        Response response = new Response();
        String responseCode = response.determineStatusLine(putRequest);
        assertEquals("HTTP/1.1 200 OK\n", responseCode);
    }

    @Test
    public void sends200OKforPOSTRequest() {
        Response response = new Response();
        reader = createBufferedReader(postRequestContents);
        postRequest = new Request(reader);
        String responseCode = response.determineStatusLine(postRequest);
        assertEquals("HTTP/1.1 200 OK\n", responseCode);
    }

    @Test
    public void allowsPUTOptionForMethodOptionsGetRequest() {
        Response response = new Response();
        reader = createBufferedReader(putRequestContents);
        Request request = new Request(reader);
        String allowedMethods = response.determineStatusLinesForMethodOptions(request);
        assertEquals("Allow: GET,HEAD,POST,OPTIONS,PUT\n", allowedMethods);
    }

    @Test
    public void allowsPUTOptionForMethodOptions2GetRequest() {
        Response response = new Response();
        getRequestContent = "GET /method_options2 HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
        reader = createBufferedReader(getRequestContent);
        Request request = new Request(reader);
        String allowedMethods = response.determineStatusLinesForMethodOptions(request);
        assertEquals("Allow: GET,OPTIONS\n", allowedMethods);
    }

    @Test
    public void buildsResponseForMethodOptions() {
        Response response = new Response();
        getRequestContent = "GET /method_options HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
        reader = createBufferedReader(getRequestContent);
        Request request = new Request(reader);
        String wholeResponse = response.get(request);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Allow: GET,HEAD,POST,OPTIONS,PUT\n", wholeResponse);
    }

    @Test
    public void buildsResponseForGetCoffee() {
        Response response = new Response();
        reader = createBufferedReader(getCoffee);
        Request request = new Request(reader);
        String wholeResponse = response.get(request);
        assertEquals("HTTP/1.1 418 I'm a teapot\n\n" +
                     "<h1> I'm a teapot</h1>\n", wholeResponse);
    }

    @Test
    public void buildsResponseBodyForGetCoffee() {
        Response response = new Response();
        reader = createBufferedReader(getCoffee);
        Request request = new Request(reader);
        String wholeResponse = response.get(request);
        assertEquals("HTTP/1.1 418 I'm a teapot\n",  wholeResponse);

    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));

    }
}
