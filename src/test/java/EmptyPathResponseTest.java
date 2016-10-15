import main.Request;
import main.responses.EmptyPathResponse;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EmptyPathResponseTest {

    private BufferedReader reader;
    List content = new ArrayList<>();
    private Request simpleGetRequest = createRequest("GET / HTTP/1.1\n");
    private Request simpleHeadRequest = createRequest("HEAD / HTTP/1.1\n");
    private Request emptyPostRequest = createRequest("POST / HTTP/1.1\n");
    private Request emptyPutRequest = createRequest("PUT / HTTP/1.1\n");
    private Request emptyOptionsRequest = createRequest("OPTIONS / HTTP/1.1\n");
    private Request emptyDeleteRequest = createRequest("DELETE / HTTP/1.1\n");

    @Test
    public void correctResponseForSimpleGet() {
        EmptyPathResponse response = new EmptyPathResponse(content);
        String createdResponse = response.get(simpleGetRequest);
        assertThat(createdResponse, containsString("HTTP 1.1 200 OK\n"));
    }

    @Test
    public void correctResponseForSimpleHead() {
        EmptyPathResponse response = new EmptyPathResponse(content);
        String createdResponse = response.head(simpleHeadRequest);
        assertEquals("HTTP 1.1 200 OK\n\n\n", createdResponse);
    }

    @Test
    public void methodNotAllowedForEmptyPost() {
        EmptyPathResponse response = new EmptyPathResponse(content);
        String createdResponse = response.post(emptyPostRequest);
        assertThat(createdResponse, containsString("HTTP 1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyPut() {
        EmptyPathResponse response = new EmptyPathResponse(content);
        String createdResponse = response.put(emptyPutRequest);
        assertThat(createdResponse, containsString("HTTP 1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyOptions() {
        EmptyPathResponse response = new EmptyPathResponse(content);
        String createdResponse = response.options(emptyOptionsRequest);
        assertThat(createdResponse, containsString("HTTP 1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyDelete() {
        EmptyPathResponse response = new EmptyPathResponse(content);
        String createdResponse = response.delete(emptyDeleteRequest);
        assertThat(createdResponse, containsString("HTTP 1.1 405 Method Not Allowed"));
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
