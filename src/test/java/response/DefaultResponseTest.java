package response;

import main.Request;
import main.responses.DefaultResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
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
        response = new DefaultResponse(content);
        getRandomRequest = helper.create("GET /chocolate");
        putEmptyRequest = helper.create("PUT /");
        postCoffeeRequest = helper.create("POST /coffee");
        randomHeadRequest = helper.create("HEAD /123");
        randomOptionsRequest = helper.create("OPTIONS /123");
        randomDeleteRequest = helper.create("DELETE /123");
    }

    @Test
    public void getChocolateRequestNotAllowed() {
        String createdResponse = response.get(getRandomRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n" +
                                                   "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                                                   "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                                                   "ETag: \n" +
                                                   "Accept-Ranges: none\n" +
                                                   "Content-Length: \n" +
                                                   "Connection: close\n" +
                                                   "Content-Type: text/plain\n\n\n"));
    }

    @Test
    public void putEmptyRequestNotAllowed() {
        String createdResponse = response.put(putEmptyRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void postCoffeeRequestNotAllowed() {
        String createdResponse = response.post(postCoffeeRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void randomHeadRequestNotAllowed() {
        String createdResponse = response.head(randomHeadRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void randomOptionsRequestNotAllowed() {
        String createdResponse = response.options(randomOptionsRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

    @Test
    public void randomDeleteRequestNotAllowed() {
        String createdResponse = response.delete(randomDeleteRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }
}
