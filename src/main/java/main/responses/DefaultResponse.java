package main.responses;

import main.Request;
import main.Response;
import main.Status;

import java.util.List;

public abstract class DefaultResponse {
    private List<String> content;
    private final String headers;

    public DefaultResponse(List content) {
        this.content = content;
        this.headers = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "ETag: \n" +
                "Accept-Ranges: none\n" +
                "Content-Length: \n" +
                "Connection: close\n" +
                "Content-Type: text/plain\n";
    }

    public String head(Request request) {
        return new Response(methodNotAllowed(), "", "").getResponse(request);
    }

    public String get(Request request) {
        return new Response(ok200(), headers, getBody(content)).getResponse(request);
    }

    public String delete(Request request) {
        return new Response(methodNotAllowed(), "", "").getResponse(request);
    }

    public String post(Request request) {
        return new Response(methodNotAllowed(), "", "").getResponse(request);
    }

    public String put(Request request) {
        return new Response(methodNotAllowed(), "", "").getResponse(request);
    }

    public String methodNotAllowed() {
        return "HTTP/1.1 405 Method Not Allowed";
    }

    public abstract String options(Request request);

    public String ok200() {
        return Status.OK.get();
    }

    public String getBody(List<String> content) {
        if (content.size() > 0) {
            return content.get(0);
        } else {
            return "";
        }
    }
}
