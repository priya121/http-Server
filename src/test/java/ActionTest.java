import main.ActionChooser;
import main.date.Date;
import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;
import main.responsetypes.FormResponse;
import main.responsetypes.NoResourceResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import responsetypes.TestDate;
import responsetypes.TestHelper;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    @After
    public void tearDown() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(publicDirectory + "/form");
        writer.print("");
        writer.close();
    }

    @Test
    public void createsSimpleGetResponse() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new NoResourceResponse(publicDirectory, testDate);
        Response responseToSend = action.determine(response, emptyGetRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: 360\n\n", responseToSend.getStatusLine() +
                                            responseToSend.getHeader());
    }

    @Test
    public void createsPutForm() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new FormResponse(publicDirectory, testDate);
        Response responseToSend = action.determine(response, postFormRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: 12\n\n\n" +
                     "data=fatcat", responseToSend.getStatusLine() +
                                    responseToSend.getHeader() +
                                    new String(responseToSend.getBody()));
    }

    @Test
    public void headResponseHasHeaders() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new NoResourceResponse(publicDirectory, testDate);
        Response responseToSend = action.determine(response, emptyHeadRequest);
        assertEquals("Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: 0\n\n", responseToSend.getHeader());
    }

    @Test
    public void createsHeadReponse() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new NoResourceResponse(publicDirectory, testDate);
        Response responseToSend = action.determine(response, emptyHeadRequest);
        assertTrue(new String(responseToSend.getBody()).isEmpty());
    }

    @Test
    public void correctResponseForBogusRequest() {
        ActionChooser action = new ActionChooser();
        DefaultResponse response = new NoResourceResponse(publicDirectory, testDate);
        String responseToSend = action.determine(response, bogusRequest).getStatusLine();
        assertEquals("HTTP/1.1 405 Method Not Allowed\n\n", responseToSend);
    }

    @Test
    public void deleteFormRequestReturnsEmptyBody() {
        ActionChooser action = new ActionChooser();
        DefaultResponse defaultResponse = new FormResponse(publicDirectory, testDate);
        action.determine(defaultResponse, postFormRequest);
        DefaultResponse response = new FormResponse(publicDirectory, testDate);
        Response responseToSend = action.determine(response, deleteFormRequest);
        assertFalse(new String(responseToSend.getBody()).contains("data=fatcat"));
    }
}
