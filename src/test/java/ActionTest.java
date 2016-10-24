import main.ActionChooser;
import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;
import main.responsetypes.EmptyPathResponse;
import main.responsetypes.FormResponse;
import org.junit.Before;
import org.junit.Test;
import response.TestHelper;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ActionTest {
    private final String publicDirectory = "/Users/priyapatil/cob_spec/public";
    private final TestHelper helper = new TestHelper();
    private ArrayList content;
    private Request emptyGetRequest;
    private Request postFormRequest;
    private Request bogusRequest;
    private Request deleteFormRequest;
    private Request emptyHeadRequest;
    private Date testDate;

    @Before
    public void setUp() {
        testDate = new TestDate();
        content = new ArrayList<>();
        emptyGetRequest = helper.create("GET /");
        postFormRequest = helper.create("POST /form");
        deleteFormRequest = helper.create("DELETE /form");
        emptyHeadRequest = helper.create("HEAD /");
        bogusRequest = helper.create("BOGUS /");
    }

    @Test
    public void createsSimpleGetResponse() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new EmptyPathResponse(content, publicDirectory, testDate);
        Response responseToSend = action.determine(response, emptyGetRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: \n\n", responseToSend.getStatusLine() +
                                            responseToSend.getHeader());
    }

    @Test
    public void createsPutForm() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new FormResponse(content, testDate);
        Response responseToSend = action.determine(response, postFormRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: \n\n\n" +
                     "data=fatcat", responseToSend.getStatusLine() +
                                    responseToSend.getHeader() +
                                    new String(responseToSend.getBody()));
    }

    @Test
    public void headResponseHasHeaders() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new EmptyPathResponse(content, publicDirectory, testDate);
        Response responseToSend = action.determine(response, emptyHeadRequest);
        assertEquals("Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: \n\n", responseToSend.getHeader());
    }

    @Test
    public void createsHeadReponse() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new EmptyPathResponse(content, publicDirectory, testDate);
        Response responseToSend = action.determine(response, emptyHeadRequest);
        assertTrue(new String(responseToSend.getBody()).isEmpty());
    }

    @Test
    public void correctResponseForBogusRequest() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new EmptyPathResponse(content, publicDirectory, testDate);
        String responseToSend = action.determine(response, bogusRequest).getStatusLine();
        assertEquals("HTTP/1.1 405 Method Not Allowed\n\n", responseToSend);
    }

    @Test
    public void deleteFormRequestReturnsEmptyBody() {
        ActionChooser action = new ActionChooser();
        DefaultResponse defaultResponse = new FormResponse(content, testDate);
        action.determine(defaultResponse, postFormRequest);
        DefaultResponse response = new FormResponse(content, testDate);
        Response responseToSend = action.determine(response, deleteFormRequest);
        assertFalse(new String(responseToSend.getBody()).contains("data=fatcat"));
    }
}
