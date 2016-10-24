package response;

import main.request.Request;
import main.responses.LogsResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogsResponseTest {
    private String publicDirectory = "/Users/priyapatil/cob_spec/public";
    TestHelper helper = new TestHelper();
    private Request unauthRequest = helper.create("GET /logs HTTP/1.1");
    private final Request authorizedRequest = helper.authorizedRequest("GET /logs HTTP/1.1");
    List content = new ArrayList<String>();
    private File filePath;
    private TemporaryFolder tempFile;

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        tempFile = new TemporaryFolder();
        tempFile.create();
        filePath = tempFile.newFile("logs");
    }

    @After
    public void tearDown() throws IOException {
        overWriteFileContents(new File(publicDirectory + "/logs"));
    }

    @Test
    public void sendsUnauthResponseFor() {
        LogsResponse response = new LogsResponse(filePath.getPath(), content);
        assertThat(response.get(unauthRequest).getStatusLine(), is("HTTP/1.1 401 Not Authorized\n"));
    }

    @Test
    public void sendsOKForAuthorizedRequest() {
        LogsResponse response = new LogsResponse(publicDirectory, content);
        assertThat(response.get(authorizedRequest).getStatusLine(), is("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void updatesContenWhenAuthCode() {
        LogsResponse response = new LogsResponse(publicDirectory, content);
        assertThat(new String(response.get(authorizedRequest).getBody()), is("GET /logs HTTP/1.1"));
    }

    private void overWriteFileContents(File file) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write("");
        writer.close();
    }
}
