import main.GetRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class GetRequestTest {
    private String getRequest;
    private BufferedReader reader;

    @Before
    public void setUp() {
        getRequest = "GET / HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
        reader = createBufferedReader(getRequest);
    }

    @Test
    public void canGetAllHeaderRequestFields() {
        GetRequest request = new GetRequest(reader);
        assertEquals("Connection: Keep-Alive\n" +
                     "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                     "Host: localhost:5000\n" +
                     "Accept-Encoding: gzip,deflate\n", request.getHeaderFields());
    }

    @Test
    public void onlyAllowsValidPaths() {

    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
