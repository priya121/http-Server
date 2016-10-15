package response;

import main.Request;
import main.responses.MethodOptions2Response;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class MethodOptions2Test {
    private BufferedReader reader;
    List content = new ArrayList<>();
    private Request getMethodOptions2 = createRequest("GET /method_options2 HTTP/1.1\n");
    private Request putMethodOptions2 = createRequest("PUT /method_options2 HTTP/1.1\n");
    private Request postMethodOptions2 = createRequest("POST /method_options2 HTTP/1.1\n");
    private Request optionsMethodOptions2 = createRequest("OPTIONS /method_options2 HTTP/1.1\n");
    private Request deleteMethodOptions2 = createRequest("DELETE /method_options2 HTTP/1.1\n");

    @Test
    public void responseForGetMethodOptions2() {
        MethodOptions2Response response = new MethodOptions2Response(content);
        String createdResponse = response.get(getMethodOptions2);
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
    public void responseForPutMethodOptions2() {
        MethodOptions2Response response = new MethodOptions2Response(content);
        String createdResponse = response.put(putMethodOptions2);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void responseForPostMethodOptions2() {
        MethodOptions2Response response = new MethodOptions2Response(content);
        String createdResponse = response.post(postMethodOptions2);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void responseForHeadMethodOptions2() {
        MethodOptions2Response response = new MethodOptions2Response(content);
        String createdResponse = response.head(postMethodOptions2);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void responseForDeleteMethodOptions2() {
        MethodOptions2Response response = new MethodOptions2Response(content);
        String createdResponse = response.delete(deleteMethodOptions2);
        assertEquals("HTTP/1.1 405 Method Not Allowed\n\n\n", createdResponse);
    }

    @Test
    public void allowIncludedInMethodOptionsRequest2() {
        MethodOptions2Response response = new MethodOptions2Response(content);
        String createdResponse = response.options(optionsMethodOptions2);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
        assertThat(createdResponse, containsString("Allow: GET,OPTIONS\n"));
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
