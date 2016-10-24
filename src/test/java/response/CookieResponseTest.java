package response;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.CookieResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CookieResponseTest {

    private CookieResponse cookieResponse;
    private Request getCookieRequest;
    TestHelper helper = new TestHelper();
    private Request getOatmealCookieRequest;
    private Date testDate;

    @Before
    public void setUp() {
        List content = new ArrayList();
        testDate = new TestDate();
        cookieResponse = new CookieResponse(content, testDate);
        getCookieRequest = helper.create("GET /cookie?type=chocolate");
        getOatmealCookieRequest = helper.create("GET /cookie?type=oatmeal");
    }

    @Test
    public void setsCookieType() {
        Response response = cookieResponse.get(getOatmealCookieRequest);
        String cookie = "Set-Cookie: type=oatmeal\n\n";
        assertThat(response.getHeader(), containsString(cookie));
    }

    @Test
    public void setsAnotherCookieTypeCookie() {
        Response response = cookieResponse.get(getCookieRequest);
        String cookie = "Set-Cookie: type=chocolate\n\n";
        assertThat(response.getHeader(), containsString(cookie));
    }
    
    @Test
    public void setsBodyIfCookieTypeChocolate() {
        Response response = cookieResponse.get(getCookieRequest);
        assertThat(response.getBody(), is("Eat".getBytes()));
    }

    @Test
    public void doesNotSetBodyTypeIfNotChocolate() {
        Response response = cookieResponse.get(getOatmealCookieRequest);
        assertThat(response.getBody(), is("".getBytes()));
    }
}
