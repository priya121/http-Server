package responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.Response;
import main.responsetypes.RedirectResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class RedirectResponseTest {

    private final TestHelper helper = new TestHelper();
    private RedirectResponse redirectResponse;
    private Request optionsRedirect;
    private Request putRedirect;
    private Request deleteRedirect;
    private Request postRedirect;
    private Request getRedirect;

    @Before
    public void setUp() {
        Date testDate = new TestDate();
        redirectResponse = new RedirectResponse(testDate);
        getRedirect = helper.create("GET /redirect");
        putRedirect = helper.create("PUT /redirect");
        postRedirect = helper.create("POST /redirect");
        optionsRedirect = helper.create("OPTIONS /redirect");
        deleteRedirect = helper.create("DELETE /redirect");
    }

    @Test
    public void redirectResponse() {
        Response createdResponse = redirectResponse.get(getRedirect);
        assertEquals("HTTP/1.1 302 Redirect\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Location: http://localhost:5000/\n" +
                     "Content-Length: 0\n\n", createdResponse.getStatusLine() +
                                              createdResponse.getHeader());
    }

    @Test
    public void redirectResponseNotAllowedForPost() {
        Response createdResponse = redirectResponse.post(postRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForPut() {
        Response createdResponse = redirectResponse.put(putRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForDelete() {
        Response createdResponse = redirectResponse.delete(deleteRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void redirectResponseNotAllowedForOptions() {
        Response createdResponse = redirectResponse.options(optionsRedirect);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }
}
