package responsetypes;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.NoResourceResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class EmptyPathResponseTest {

    private NoResourceResponse emptyPathResponse;
    private final TestHelper helper = new TestHelper();
    private final Request simpleGetRequest = helper.create("GET /");
    private final Request simpleHeadRequest = helper.create("HEAD /");
    private final Request emptyPostRequest = helper.create("POST /");
    private final Request emptyPutRequest = helper.create("PUT /");
    private final Request emptyOptionsRequest = helper.create("OPTIONS /");
    private final Request emptyDeleteRequest = helper.create("DELETE /");

    @Before
    public void setUp() {
        Date testDate = new TestDate();
        String publicDirectory = "/Users/priyapatil/cob_spec/public";
        emptyPathResponse = new NoResourceResponse(publicDirectory, testDate);
    }

    @Test
    public void correctResponseForSimpleGet() {
        Response createdResponse = emptyPathResponse.get(simpleGetRequest);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void correctResponseForSimpleHead() {
        Response createdResponse = emptyPathResponse.head(simpleHeadRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: 0\n\n", createdResponse.getStatusLine() +
                                                     createdResponse.getHeader());
    }

    @Test
    public void methodNotAllowedForEmptyPost() {
        Response createdResponse = emptyPathResponse.post(emptyPostRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void methodNotAllowedForEmptyPut() {
        Response createdResponse = emptyPathResponse.put(emptyPutRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void methodNotAllowedForEmptyOptions() {
        Response createdResponse = emptyPathResponse.options(emptyOptionsRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void methodNotAllowedForEmptyDelete() {
        Response createdResponse = emptyPathResponse.delete(emptyDeleteRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 404 Not Found\n"));
    }

    @Test
    public void emptyGetDisplaysImageFile1Link() {
        Response createdResponse = emptyPathResponse.get(simpleGetRequest);
        String bodyContents = new String(createdResponse.getBody());
        assertThat(bodyContents, containsString("<a href=/file1>/file1</a>\n"));
    }

    @Test
    public void emptyGetDisplaysFile2Link() {
        Response createdResponse = emptyPathResponse.get(simpleGetRequest);
        String bodyContents = new String(createdResponse.getBody());
        assertThat(bodyContents, containsString("<a href=/file2>/file2</a>\n"));
    }

    @Test
    public void emptyGetDisplaysImageLink() {
        Response createdResponse = emptyPathResponse.get(simpleGetRequest);
        String bodyContents = new String(createdResponse.getBody());
        assertThat(bodyContents, containsString("<a href=/image.gif>/image.gif</a>\n"));
    }

    @Test
    public void emptyGetDisplaysAllFileLinks() {
        Response createdResponse = emptyPathResponse.get(simpleGetRequest);
        String bodyContents = new String(createdResponse.getBody());
        assertThat(bodyContents, containsString("<a href=/image.png>/image.png</a>\n"));
        assertThat(bodyContents, containsString("<a href=/image.jpeg>/image.jpeg</a>\n"));
        assertThat(bodyContents, containsString("<a href=/text-file.txt>/text-file.txt</a>\n"));
    }
}
