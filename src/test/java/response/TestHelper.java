package response;

import main.Request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class TestHelper {
    private final static String header = "Host: localhost:5000\n" +
                                         "Connection: Keep-Alive\n" +
                                         "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                                         "Accept-Encoding: gzip,deflate";

    public Request create(String requestLine) {
        String requestContent = requestLine + " HTTP/1.1\n" +
                                header;
        BufferedReader reader = createBufferedReader(requestContent);
        return new Request(reader);
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
