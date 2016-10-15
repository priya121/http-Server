import main.Request;
import main.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseTest {
    private String okResponse;
    private Request getRequest = createRequest("GET / HTTP/1.1\n");
    private Request getForm = createRequest("GET /form HTTP/1.1\n");
    private Request getRedirect = createRequest("GET /redirect HTTP/1.1\n");
    private Request getMethodOptions = createRequest("GET /method_options HTTP/1.1\n");
    private Request getMethodOptions2 = createRequest("GET /method_options2 HTTP/1.1\n");
    private Request getCoffee = createRequest("GET /coffee HTTP/1.1\n");
    private Request putForm = createRequest("PUT /form HTTP/1.1\n");
    private Request putFile1= createRequest("PUT /file1 HTTP/1.1\n");
    private Request postForm = createRequest("POST /form HTTP/1.1\n");
    private Request postTxtFile = createRequest("POST /text-file.txt HTTP/1.1\n");
    private BufferedReader reader;
    private ArrayList<String> content;

    @Before
    public void setUp() {
        okResponse = createResponse();
        content = new ArrayList<>();
    }

    @Test
    public void sends200OKtoGETRequest() {
        Response response = new Response(content);
        String responseCode = response.determineStatusLine(putForm);
        assertEquals("HTTP/1.1 200 OK\n", responseCode);
    }

    @Test
    public void sends200OKforPUTRequest() {
        Response response = new Response(content);
        String responseCode = response.determineStatusLine(putForm);
        assertEquals("HTTP/1.1 200 OK\n", responseCode);
    }

    @Test
    public void sends200OKforPOSTRequest() {
        Response response = new Response(content);
        String responseCode = response.determineStatusLine(postForm);
        assertEquals("HTTP/1.1 200 OK\n", responseCode);
    }

    @Test
    public void allowsDataToBeSentWithPostRequest() {
        Response response = new Response(content);
        response.storeContent(postForm);
        String content = response.getContent(getForm);
        assertEquals("\ndata=fatcat", content.toString());
    }

    @Test
    public void allowsPutRequestToOverWriteExistingData() {
       Response response = new Response(content);
       response.storeContent(postForm);
       response.storeContent(putForm);
       String content = response.getContent(getForm);
       assertTrue(response.size() == 1);
       assertEquals("\ndata=heathcliff", content.toString());
    }

    @Test
    public void allowsDifferentDataToBeSentWithPostRequest() {
        Response response = new Response(content);
        response.storeContent(postForm);
        String content = response.getContent(getForm);
        assertEquals("\ndata=fatcat", content.toString());
    }
    
    @Test
    public void deleteRequestDeletesFormContent() {
       List content = new ArrayList<>(Arrays.asList("\ndata=fatcat"));
       Response response = new Response(content);
       Request deleteFormRequest = createRequest("DELETE /form HTTP/1.1\n");
       response.storeContent(deleteFormRequest);
       String retrieveContent = response.getContent(getRequest);
       assertTrue(response.size() == 0);
       assertThat(retrieveContent.toString(), is(""));
    }

    @Test
    public void allowsPUTOptionForMethodOptionsGetRequest() {
        Response response = new Response(content);
        String allowedMethods = response.determineStatusLinesForMethodOptions(getMethodOptions);
        assertEquals("Allow: GET,HEAD,POST,OPTIONS,PUT\n", allowedMethods);
    }

    @Test
    public void allowsPUTOptionForMethodOptions2GetRequest() {
        Response response = new Response(content);
        String allowedMethods = response.determineStatusLinesForMethodOptions(getMethodOptions2);
        assertEquals("Allow: GET,OPTIONS\n", allowedMethods);
    }

    @Test
    public void buildsResponseForMethodOptions() {
        Response response = new Response(content);
        String wholeResponse = response.get(getMethodOptions);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Allow: GET,HEAD,POST,OPTIONS,PUT\n", wholeResponse);
    }

    @Test
    public void buildsResponseForGetCoffee() {
        Response response = new Response(content);
        String wholeResponse = response.get(getCoffee);
        assertEquals("HTTP/1.1 418 I'm a teapot\n\n" +
                     "<h1> I'm a teapot</h1>\n", wholeResponse);
    }

    @Test
    public void buildsRedirectResponse() {
        Response response = new Response(content);
        String wholeResponse = response.get(getRedirect);
        assertEquals("HTTP/1.1 302\nLocation: http://localhost:5000/\n", wholeResponse);
    }

    @Test
    public void returnsMethodNotAllowedForPutFile1() {
        Response response = new Response(content);
        String wholeResponse = response.get(putFile1);
        assertEquals("HTTP/1.1 405 Method Not Allowed\n",  wholeResponse);
    }

    @Test
    public void returnsMethodNotAllowedForPostTextFile() {
        Response response = new Response(content);
        String wholeResponse = response.get(postTxtFile);
        assertEquals("HTTP/1.1 405 Method Not Allowed\n",  wholeResponse);
    }

    private String createResponse() {
        return "HTTP/1.1 200 OK\n" +
               "Date: Mon, 27 Jul 2009 12:28:53 GMT\n" +
               "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
               "Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT\n" +
               "Content-Length: 11\n" +
               "Content-Type: text/html\n" +
               "Connection: Closed";
    }

    private Request createRequest(String requestLine) {
        String requestContent = requestLine +
               "Host: localhost:5000\n" +
               "Connection: Keep-Alive\n" +
               "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
               "Accept-Encoding: gzip,deflate";
        reader = createBufferedReader(requestContent);
        return new Request(reader);
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
