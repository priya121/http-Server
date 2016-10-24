package response;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.LogsResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogsResponseTest {
    private String publicDirectory = "/Users/priyapatil/cob_spec/public";
    TestHelper helper = new TestHelper();
    private Request unauthRequest = helper.create("GET /logs HTTP/1.1");
    private final Request authRequest = helper.authorizedRequest("GET /logs HTTP/1.1");
    private Date testDate;
    private ArrayList<String> content;

    @Before
    public void setUp() throws IOException {
        testDate = new TestDate();
        content = new ArrayList<String>();
    }

    @After
    public void tearDown() throws IOException {
        overWriteFileContents(new File(publicDirectory + "/logs"));
    }

    @Test
    public void sendsUnauthResponseFor() {
        LogsResponse response = new LogsResponse(publicDirectory, content, testDate);
        Response responseToSend = response.get(unauthRequest);
        assertThat(responseToSend.getStatusLine(), is("HTTP/1.1 401 Not Authorized\n"));
    }

    @Test
    public void sendsOKForAuthorizedRequest() {
        LogsResponse response = new LogsResponse(publicDirectory, content, testDate);
        Response responseToSend = response.get(authRequest);
        assertThat(responseToSend.getStatusLine(), is("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void updatesContentWhenAuthCode() {
        LogsResponse response = new LogsResponse(publicDirectory, content, testDate);
        Response responseToSend = response.get(authRequest);
        assertThat(new String(responseToSend.getBody()), is("GET /logs HTTP/1.1"));
    }

    @Test
    public void doesNotUpdateIfNotAuthorized() {
        LogsResponse response = new LogsResponse(publicDirectory, content, testDate);
        Response responseToSend = response.get(unauthRequest);
        assertThat(new String(responseToSend.getBody()), is(""));
    }

    private void overWriteFileContents(File file) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write("");
        writer.close();
    }
}
