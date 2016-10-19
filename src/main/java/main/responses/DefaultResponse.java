package main.responses;

import main.Request;
import main.Response;

import java.util.List;

import static main.Status.METHOD_NOT_ALLOWED;

public class DefaultResponse {
    List<String> content;
    private final String defaultHeaders;

    public DefaultResponse(List content) {
        this.content = content;
        this.defaultHeaders = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                              "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                              "ETag: \n" +
                              "Accept-Ranges: none\n" +
                              "Content-Length: \n" +
                              "Connection: close\n" +
                              "Content-Type: \n";
    }

    public Response get(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            body(getBody(content)));
    }

    public Response head(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            body(getBody(content)));
    }

    public Response delete(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            body(getBody(content)));

    }

    public Response post(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            body(getBody(content)));
    }

    public Response put(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            body(getBody(content)));
    }

    public Response options(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            body(getBody(content)));
    }

    public Response patch(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                defaultHeaders,
                body(getBody(content)));
    }

    public String getBody(List<String> content) {
        if (content.size() > 0) {
            return content.get(0);
        } else {
            return "";
        }
    }

    public byte[] body(String content) {
        return content.getBytes();
    }
}
