import main.Action;
import main.Request;
import main.responses.DefaultResponse;
import main.responses.EmptyPathResponse;
import org.junit.Before;
import org.junit.Test;
import response.TestHelper;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ActionTest {
    private final TestHelper helper = new TestHelper();
    private ArrayList content;
    private Request emptyGetRequest;
    private Request postFormRequest;

    @Before
    public void setUp() {
        content = new ArrayList<>();
        emptyGetRequest = helper.create("GET /");
    }

    @Test
    public void executesGetMethod() {
        Action action = new Action();
        DefaultResponse response = new EmptyPathResponse(content);
        String responseToSend = action.determine(response, emptyGetRequest);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "ETag: \n" +
                     "Accept-Ranges: none\n" +
                     "Content-Length: \n" +
                     "Connection: close\n" +
                     "Content-Type: text/plain\n\n\n", responseToSend);
    }
}
