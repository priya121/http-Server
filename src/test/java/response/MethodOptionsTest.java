package response;

import main.Request;
import main.responses.MethodOptionsResponse;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MethodOptionsTest {

    private BufferedReader reader;
    List content = new ArrayList<>();
    private Request getMethodOptions = createRequest("GET /method_options HTTP/1.1\n");
    private Request putMethodOptions = createRequest("PUT /method_options HTTP/1.1\n");
    private Request postMethodOptions = createRequest("POST /method_options HTTP/1.1\n");
    private Request optionsMethodOptions = createRequest("OPTIONS /method_options HTTP/1.1\n");
    private Request deleteMethodOptions = createRequest("DELETE /method_options HTTP/1.1\n");

    @Test
    public void responseForGetMethodOptions() {
        MethodOptionsResponse response = new MethodOptionsResponse(content);
        String createdResponse = response.get(getMethodOptions);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n\n", createdResponse);
    }

    @Test
    public void responseForPutMethodOptions() {
        MethodOptionsResponse response = new MethodOptionsResponse(content);
        String createdResponse = response.put(putMethodOptions);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void responseForPostMethodOptions() {
        MethodOptionsResponse response = new MethodOptionsResponse(content);
        String createdResponse = response.post(postMethodOptions);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void responseForHeadMethodOptions() {
        MethodOptionsResponse response = new MethodOptionsResponse(content);
        String createdResponse = response.head(postMethodOptions);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void responseForDeleteMethodOptions() {
        MethodOptionsResponse response = new MethodOptionsResponse(content);
        String createdResponse = response.delete(deleteMethodOptions);
        assertEquals("HTTP/1.1 405 Method Not Allowed\n\n\n", createdResponse);
    }

    @Test
    public void allowIncludedInMethodOptionsRequest() {
        MethodOptionsResponse response = new MethodOptionsResponse(content);
        String createdResponse = response.options(optionsMethodOptions);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
        assertThat(createdResponse, containsString("Allow: GET,HEAD,POST,OPTIONS,PUT\n"));
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
