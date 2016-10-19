package response;

import main.request.Request;
import main.Response;
import main.responses.NoResourceResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class NoResourceResponseTest {
    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();
    private NoResourceResponse response;
    private Request getFoobar;
    private Request headFoobar;
    private Request postFoobar;
    private Request optionsFoobar;
    private Request deleteFoobar;
    private Request putFoobar;

    @Before
    public void setUp() {
        response = new NoResourceResponse(content);
        getFoobar = helper.create("GET /foobar");
        headFoobar = helper.create("HEAD /foobar");
        putFoobar = helper.create("PUT /foobar");
        postFoobar = helper.create("POST /foobar");
        optionsFoobar = helper.create("OPTIONS /foobar");
        deleteFoobar = helper.create("DELETE /foobar");
    }

    @Test
    public void getNonExistentResource() {
        Response createdResponse = response.get(getFoobar);
        assertEquals("HTTP/1.1 404 Not Found\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n", createdResponse.getHeader());
        }

    @Test
    public void headNonExistentResource() {
        Response createdResponse = response.head(headFoobar);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void postNonExistentResource() {
        Response createdResponse = response.post(postFoobar);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void deleteNonExistentResource() {
        Response createdResponse = response.delete(deleteFoobar);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void optionsNonExistentResource() {
        Response createdResponse = response.options(optionsFoobar);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }
}
