package response;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DefaultResponseTest {
    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();
    private DefaultResponse response;
    private Request getRandomRequest;
    private Request putEmptyRequest;
    private Request postCoffeeRequest;
    private Request randomHeadRequest;
    private Request randomOptionsRequest;
    private Request randomDeleteRequest;

    @Before
    public void setUp() {
        Date testDate = new TestDate();
        response = new DefaultResponse(content, testDate);

        getRandomRequest = helper.create("GET /chocolate");
        putEmptyRequest = helper.create("PUT /");
        postCoffeeRequest = helper.create("POST /coffee");
        randomHeadRequest = helper.create("HEAD /123");
        randomOptionsRequest = helper.create("OPTIONS /123");
        randomDeleteRequest = helper.create("DELETE /123");
    }

    @Test
    public void getChocolateRequestNotAllowed() {
        Response createdResponse = response.get(getRandomRequest);
        assertThat(createdResponse.getStatusLine() +
                   createdResponse.getHeader(), containsString("Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                                                               "Content-Length: \n\n"));
    }

    @Test
    public void putEmptyRequestNotAllowed() {
        Response createdResponse = response.put(putEmptyRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void postCoffeeRequestNotAllowed() {
        Response createdResponse = response.post(postCoffeeRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void randomHeadRequestNotAllowed() {
        Response createdResponse = response.head(randomHeadRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void randomOptionsRequestNotAllowed() {
        Response createdResponse = response.options(randomOptionsRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void randomDeleteRequestNotAllowed() {
        Response createdResponse = response.delete(randomDeleteRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 405 Method Not Allowed\n"));
    }
}
