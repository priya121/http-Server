package response;

import main.response.Response;
import main.request.Request;
import main.responsetypes.DefaultResponse;
import main.responsetypes.GetCookieResponse;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetCookieResponseTest {

    public String header = "Host: localhost:5000\n" +
                                     "Connection: Keep-Alive\n" +
                                     "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                                     "Accept-Encoding: gzip,deflate";


    public Request create(String requestLine, String cookieType) {
        String requestContent = requestLine + " HTTP/1.1\n" +
                                header + "\nCookie: type=" + cookieType + "\n";
        BufferedReader reader = createBufferedReader(requestContent);
        return new Request(reader);
    }

    @Test
    public void getWithCookieRespondsWithCorrectBody() {
        Request getCookieRequest = create("GET /eat_cookie", "chocolate");
        List content = new ArrayList();
        DefaultResponse getCookieResponse = new GetCookieResponse(content);
        Response response = getCookieResponse.get(getCookieRequest);
        assertThat(response.getBody(), is("mmmm chocolate".getBytes()));
    }

    @Test
    public void getWithAnotherCookieRespondsAccordingly() {
        Request getCookieRequest = create("GET /eat_cookie", "oatmeal");
        List content = new ArrayList();
        DefaultResponse getCookieResponse = new GetCookieResponse(content);
        Response response = getCookieResponse.get(getCookieRequest);
        assertThat(response.getBody(), is("".getBytes()));
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
