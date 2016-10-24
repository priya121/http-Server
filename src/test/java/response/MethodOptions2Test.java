package response;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.MethodOptions2Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class MethodOptions2Test {

    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();

    private Request getMethodOptions2 = helper.create("GET /method_options2");
    private Request putMethodOptions2 = helper.create("PUT /method_options2");
    private Request postMethodOptions2 = helper.create("POST /method_options2");
    private Request optionsMethodOptions2 = helper.create("OPTIONS /method_options2");
    private Request deleteMethodOptions2 = helper.create("DELETE /method_options2");
    private MethodOptions2Response response;

    @Before
    public void setUp() {
        Date testDate = new TestDate();
        response = new MethodOptions2Response(content, testDate);
    }

    @Test
    public void optionsMethodOptionsRequest2() {
        Response createdResponse = response.options(optionsMethodOptions2);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 200 OK\n"));
        assertThat(createdResponse.getHeader(), containsString("Allow: GET,OPTIONS\n"));
    }

    @Test
    public void getMethodOptions2() {
        Response createdResponse = response.get(getMethodOptions2);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: \n" +
                     "Allow: GET,OPTIONS\n\n", createdResponse.getStatusLine() +
                                               createdResponse.getHeader());
    }

    @Test
    public void putMethodOptions2() {
        Response createdResponse = response.put(putMethodOptions2);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void postMethodOptions2() {
        Response createdResponse = response.post(postMethodOptions2);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void headMethodOptions2() {
        Response createdResponse = response.head(postMethodOptions2);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 405 Method Not Allowed"));
    }

    @Test
    public void deleteMethodOptions2() {
        Response createdResponse = response.delete(deleteMethodOptions2);
        assertEquals("HTTP/1.1 405 Method Not Allowed\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Content-Length: \n\n", createdResponse.getStatusLine() +
                                             createdResponse.getHeader());
    }
}
