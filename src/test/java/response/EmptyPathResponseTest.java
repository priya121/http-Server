package response;

import main.Request;
import main.responses.EmptyPathResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EmptyPathResponseTest {

    private EmptyPathResponse response;
    private final List content = new ArrayList<>();
    private final TestHelper helper = new TestHelper();
    private final Request simpleGetRequest = helper.create("GET /");
    private final Request simpleHeadRequest = helper.create("HEAD /");
    private final Request emptyPostRequest = helper.create("POST /");
    private final Request emptyPutRequest = helper.create("PUT /");
    private final Request emptyOptionsRequest = helper.create("OPTIONS /");
    private final Request emptyDeleteRequest = helper.create("DELETE /");

    @Before
    public void setUp() {
        response = new EmptyPathResponse(content);
    }

    @Test
    public void correctResponseForSimpleGet() {
        String createdResponse = response.get(simpleGetRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void correctResponseForSimpleHead() {
        String createdResponse = response.head(simpleHeadRequest);
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
    public void methodNotAllowedForEmptyPost() {
        String createdResponse = response.post(emptyPostRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyPut() {
        String createdResponse = response.put(emptyPutRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyOptions() {
        String createdResponse = response.options(emptyOptionsRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyDelete() {
        String createdResponse = response.delete(emptyDeleteRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }
}
