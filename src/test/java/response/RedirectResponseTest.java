package response;

import main.response.Response;
import main.request.Request;
import main.responsetypes.RedirectResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
                     "Date: \n" +
                     "Content-Length: \n" +
                     "Content-Type: \n" +
                     "Location: http://localhost:5000/\n\n", createdResponse.getStatusLine() +
                                                     createdResponse.getHeader());
    }

    @Test
    public void redirectResponseNotAllowedForPost() {
        Response createdResponse = response.post(postRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForPut() {
        Response createdResponse = response.put(putRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForDelete() {
        Response createdResponse = response.delete(deleteRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForOptions() {
        Response createdResponse = response.options(optionsRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }
}
