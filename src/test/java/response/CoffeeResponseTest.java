package response;

import main.Request;
import main.Response;
import main.responses.CoffeeResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class CoffeeResponseTest {

    private final TestHelper helper = new TestHelper();
    private final List content = new ArrayList<>();
    private CoffeeResponse response;
    private Request getCoffeeRequest;
    private Request postCoffeeRequest;

    @Before
    public void setUp() {
        response = new CoffeeResponse(content);
        getCoffeeRequest = helper.create("GET /coffee");
        postCoffeeRequest = helper.create("POST /coffee");
    }

    @Test
    public void correctResponseForSimpleGet() {
        Response createdResponse = response.get(getCoffeeRequest);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 418 I'm a teapot\n"));
    }

    @Test
    public void methodNotAllowedForPostCoffee() {
        Response createdResponse = response.post(postCoffeeRequest);
        assertThat(createdResponse.getHeader(), containsString("HTTP/1.1 405 Method Not Allowed\n"));
    }

}
