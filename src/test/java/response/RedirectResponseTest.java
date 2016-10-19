package response;

import main.request.Request;
import main.Response;
import main.responses.RedirectResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class RedirectResponseTest {

    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();
    private RedirectResponse response;
    private Request optionsRedirect;
    private Request putRedirect;
    private Request deleteRedirect;
    private Request postRedirect;
    private Request getRedirect;

    @Before
    public void setUp() {
        response = new RedirectResponse(content);
        getRedirect = helper.create("GET /redirect");
        putRedirect = helper.create("PUT /redirect");
        postRedirect = helper.create("POST /redirect");
        optionsRedirect = helper.create("OPTIONS /redirect");
        deleteRedirect = helper.create("DELETE /redirect");
    }

    @Test
    public void redirectResponse() {
        Response createdResponse = response.get(getRedirect);
        assertEquals("HTTP/1.1 302 Redirect\n" +
                     "Location: http://localhost:5000/\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n", createdResponse.getHeader());
    }

    @Test
    public void redirectResponseNotAllowedForPost() {
        Response createdResponse = response.post(postRedirect);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForPut() {
        Response createdResponse = response.put(putRedirect);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForDelete() {
        Response createdResponse = response.delete(deleteRedirect);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForOptions() {
        Response createdResponse = response.options(optionsRedirect);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }
}
