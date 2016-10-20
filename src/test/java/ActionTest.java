import main.ActionChooser;
import main.request.Request;
import main.Response;
import main.responses.DefaultResponse;
import main.responses.EmptyPathResponse;
import main.responses.FormResponse;
import org.junit.Before;
import org.junit.Test;
import response.TestHelper;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ActionTest {
    private final String publicDirectory = "/Users/priyapatil/cob_spec/public";
    private final TestHelper helper = new TestHelper();
    private ArrayList content;
    private Request emptyGetRequest;
    private Request postFormRequest;
    private Request bogusRequest;
    private Request deleteFormRequest;

    @Before
    public void setUp() {
        content = new ArrayList<>();
        emptyGetRequest = helper.create("GET /");
        postFormRequest = helper.create("POST /form");
        deleteFormRequest = helper.create("DELETE /form");
        bogusRequest = helper.create("BOGUS /");
    }

    @Test
    public void createsSimpleGetResponse() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new EmptyPathResponse(content, publicDirectory);
        Response responseToSend = action.determine(response, emptyGetRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n", responseToSend.getStatusLine() +
                                                     responseToSend.getHeader());
    }

    @Test
    public void createsPutForm() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new FormResponse(content);
        Response responseToSend = action.determine(response, postFormRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "ETag: \n" +
                "Accept-Ranges: none\n" +
                "Content-Length: \n" +
                "Connection: close\n" +
                "Content-Type: text/plain\n\n\n" +
                "data=fatcat", responseToSend.getStatusLine() +
                               responseToSend.getHeader() +
                               new String(responseToSend.getBody()));
    }

    @Test
    public void correctResponseForBogusRequest() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new EmptyPathResponse(content, publicDirectory);
        String responseToSend = action.determine(response, bogusRequest).getStatusLine();
        assertEquals("HTTP/1.1 405 Method Not Allowed\n\n", responseToSend);
    }

    @Test
    public void deleteFormRequestReturnsEmptyBody() {
        ActionChooser action = new ActionChooser();
        DefaultResponse defaultResponse = new FormResponse(content);
        action.determine(defaultResponse, postFormRequest);
        DefaultResponse response = new FormResponse(content);
        Response responseToSend = action.determine(response, deleteFormRequest);
        assertFalse(new String(responseToSend.getBody()).contains("data=fatcat"));
    }
}
