package responsetypes;

import main.date.Date;
import main.date.TestDate;
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
    private final String publicDirectory = "/Users/priyapatil/cob_spec/public";
    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();
    private NoResourceResponse noResourceResponse;
    private Request getFoobar;
    private Request headFoobar;
    private Request postFoobar;
    private Request optionsFoobar;
    private Request deleteFoobar;
    private Date testDate;

    @Before
    public void setUp() {
        testDate = new TestDate();
        noResourceResponse = new NoResourceResponse(publicDirectory, testDate);
        getFoobar = helper.create("GET /foobar");
        headFoobar = helper.create("HEAD /foobar");
        postFoobar = helper.create("POST /foobar");
        optionsFoobar = helper.create("OPTIONS /foobar");
        deleteFoobar = helper.create("DELETE /foobar");
    }

    @Test
    public void getNonExistentResource() {
        Response createdResponse = noResourceResponse.get(getFoobar);
        assertEquals("HTTP/1.1 404 Not Found\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: 0\n\n", createdResponse.getStatusLine() +
                                           createdResponse.getHeader());
    }

    @Test
    public void headNonExistentResource() {
        Response createdResponse = noResourceResponse.head(headFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void postNonExistentResource() {
        Response createdResponse = noResourceResponse.post(postFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void deleteNonExistentResource() {
        Response createdResponse = noResourceResponse.delete(deleteFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void optionsNonExistentResource() {
        Response createdResponse = noResourceResponse.options(optionsFoobar);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }
}