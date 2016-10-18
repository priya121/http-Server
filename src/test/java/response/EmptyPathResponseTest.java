package response;

import main.Request;
import main.Response;
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
        Response createdResponse = response.get(simpleGetRequest);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void correctResponseForSimpleHead() {
        Response createdResponse = response.head(simpleHeadRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n", createdResponse.getHeader());
    }

    @Test
    public void methodNotAllowedForEmptyPost() {
        Response createdResponse = response.post(emptyPostRequest);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyPut() {
        Response createdResponse = response.put(emptyPutRequest);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyOptions() {
        Response createdResponse = response.options(emptyOptionsRequest);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void methodNotAllowedForEmptyDelete() {
        Response createdResponse = response.delete(emptyDeleteRequest);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }
}
