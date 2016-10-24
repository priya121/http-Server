package response;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.TeaResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TeaResponseTest {
    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();
    private TeaResponse teaResponse;
    private Request getTeaRequest;

    @Before
    public void setUp() {
        Date testDate = new TestDate();
       teaResponse = new TeaResponse(content, testDate);
       getTeaRequest = helper.create("GET /tea");
    }

    @Test
    public void correctResponseForSimpleGet() {
        Response createdResponse = teaResponse.get(getTeaRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void postReturnsMethodNotAllowed() {
        Response createdResponse = teaResponse.post(getTeaRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void putReturnsMethodNotAllowed() {
        Response createdResponse = teaResponse.put(getTeaRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }
}
