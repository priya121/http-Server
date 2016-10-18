package main.responses;

import main.Request;
import main.Response;

import java.util.List;

import static main.Status.NOT_FOUND;

public class NoResourceResponse extends DefaultResponse {
    private final List<String> content;
    private final String headers;

    public NoResourceResponse(List content) {
        super(content);
        this.content = content;
        this.headers = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                       "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                       "ETag: \n" +
                       "Accept-Ranges: none\n" +
                       "Content-Length: \n" +
                       "Connection: close\n" +
                       "Content-Type: text/plain\n";
    }

    @Override
    public Response get(Request request) {
        return new Response(NOT_FOUND.get(),
                headers,
                body(getBody(content)));
    }

    @Override
    public Response head(Request request) {
        return new Response(NOT_FOUND.get(),
                headers,
                body(getBody(content)));
    }

    @Override
    public Response delete(Request request) {
        return new Response(NOT_FOUND.get(),
                headers,
                body(getBody(content)));
    }
}
