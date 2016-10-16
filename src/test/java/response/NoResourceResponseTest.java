package response;

import main.Request;
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
        String createdResponse = response.get(getFoobar);
        assertEquals("HTTP/1.1 404 Not Found\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n\n", createdResponse);
        }

    @Test
    public void headNonExistentResource() {
        String createdResponse = response.head(headFoobar);
        assertThat(createdResponse, containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void postNonExistentResource() {
        String createdResponse = response.post(postFoobar);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void deleteNonExistentResource() {
        String createdResponse = response.delete(deleteFoobar);
        assertThat(createdResponse, containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void optionsNonExistentResource() {
        String createdResponse = response.options(optionsFoobar);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed"));
    }
}
