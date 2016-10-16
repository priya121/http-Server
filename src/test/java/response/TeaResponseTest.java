package response;

import main.Request;
import main.responses.TeaResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class TeaResponseTest {
    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();
    private TeaResponse response;
    private Request getTeaRequest;

    @Before
    public void setUp() {
       response = new TeaResponse(content);
        getTeaRequest = helper.create("GET /tea");
    }

    @Test
    public void correctResponseForSimpleGet() {
        String createdResponse = response.get(getTeaRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void postReturnsMethodNotAllowed() {
        String createdResponse = response.post(getTeaRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void putReturnsMethodNotAllowed() {
        String createdResponse = response.put(getTeaRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }
}
