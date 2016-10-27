package responsetypes;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.GetCookieResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetCookieResponseTest {
    private GetCookieResponse getCookieResponse;
    TestHelper helper = new TestHelper();
    private Request getCookieRequest;
    private Request getOatmealCookie;


    @Before
    public void setUp() {
        Date testDate = new TestDate();
        getCookieResponse = new GetCookieResponse(testDate);
        getCookieRequest = helper.createRequestWithCookie("GET /eat_cookie", "chocolate");
        getOatmealCookie = helper.createRequestWithCookie("GET /eat_cookie", "oatmeal");
    }

    @Test
    public void getWithCookieRespondsWithCorrectBody() {
        Response responseToSend = getCookieResponse.get(getCookieRequest);
        assertThat(responseToSend.getBody(), is("mmmm chocolate".getBytes()));
    }

    @Test
    public void getWithAnotherCookieRespondsAccordingly() {
        Response responseToSend = getCookieResponse.get(getOatmealCookie);
        assertThat(responseToSend.getBody(), is("".getBytes()));
    }
}
