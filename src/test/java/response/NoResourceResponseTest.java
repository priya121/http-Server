package response;

import main.request.Request;
import main.response.Response;
import main.responsetypes.NoResourceResponse;
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

    @Before
    public void setUp() {
        response = new NoResourceResponse(content);
        getFoobar = helper.create("GET /foobar");
        headFoobar = helper.create("HEAD /foobar");
        postFoobar = helper.create("POST /foobar");
        optionsFoobar = helper.create("OPTIONS /foobar");
        deleteFoobar = helper.create("DELETE /foobar");
    }

    @Test
    public void getNonExistentResource() {
        Response createdResponse = response.get(getFoobar);
        assertEquals("HTTP/1.1 404 Not Found\n" +
                     "Date: \n" +
                     "Content-Length: \n" +
                     "Content-Type: \n\n", createdResponse.getStatusLine() +
                                                     createdResponse.getHeader());
    }

    @Test
    public void headNonExistentResource() {
        Response createdResponse = response.head(headFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void postNonExistentResource() {
        Response createdResponse = response.post(postFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void deleteNonExistentResource() {
        Response createdResponse = response.delete(deleteFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void optionsNonExistentResource() {
        Response createdResponse = response.options(optionsFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }
}