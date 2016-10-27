package responsetypes;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.NoResourceResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MethodOptionsTest {

    private final TestHelper helper = new TestHelper();
    private NoResourceResponse methodOptionsResponse;
    private Request getOptions;
    private Request postOptions;
    private Request optionsOptions;

    @Before
    public void setUp() {
        Date testDate = new TestDate();
        String publicDirectory = "/Users/priyapatil/cob_spec/public";
        methodOptionsResponse = new NoResourceResponse(publicDirectory, testDate);

        getOptions = helper.create("GET /method_options");
        postOptions = helper.create("POST /method_options");
        optionsOptions = helper.create("OPTIONS /method_options");
    }

    @Test
    public void responseForGetMethodOptions() {
        Response createdResponse = methodOptionsResponse.get(getOptions);
        assertEquals("HTTP/1.1 200 OK\n" +
                     "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                     "Allow: GET,HEAD,POST,OPTIONS,PUT\n" +
                     "Content-Length: 0\n\n", createdResponse.getStatusLine() +
                                              createdResponse.getHeader());
    }

    @Test
    public void responseForHeadMethodOptions() {
        Response createdResponse = methodOptionsResponse.head(postOptions);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void allowIncludedInMethodOptionsRequest() {
        Response createdResponse = methodOptionsResponse.options(optionsOptions);
        assertThat(createdResponse.getStatusLine(), containsString("HTTP/1.1 200 OK\n"));
        assertThat(createdResponse.getHeader(), containsString("Allow: GET,HEAD,POST,OPTIONS,PUT\n"));
    }
}
