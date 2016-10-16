package main.responses;

import main.Request;
import main.Response;

import java.util.List;

import static main.Status.REDIRECT;

public class RedirectResponse extends DefaultResponse {
    private final List content;
    private String headers;

    public RedirectResponse(List content) {
        super(content);
        this.content = content;
        this.headers = "Location: http://localhost:5000/\n" +
                "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "ETag: \n" +
                "Accept-Ranges: none\n" +
                "Content-Length: \n" +
                "Connection: close\n" +
                "Content-Type: text/plain\n";
    }

    @Override
    public String get(Request request) {
        return new Response(REDIRECT.get(),
                            headers,
                            getBody(content)).getResponse();
    }
}
