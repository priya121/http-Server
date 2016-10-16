package main.responses;

import main.Request;
import main.Response;

import java.util.List;

import static main.Status.OK;

public class FormResponse extends DefaultResponse {
    private final List<String> content;
    private final String headers;

    public FormResponse(List<String> content) {
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
    public String get(Request request) {
        return new Response(OK.get(),
                            headers,
                            getBody(content)).getResponse();
    }

    @Override
    public String post(Request request) {
        content.add(0, "\ndata=fatcat");
        return new Response(OK.get(),
                            headers,
                            getBody(content)).getResponse();
    }

    @Override
    public String put(Request request) {
        removePreviousData();
        content.add(0, "\ndata=heathcliff");
        return new Response(OK.get(),
                            headers,
                            getBody(content)).getResponse();
    }

    @Override
    public String delete(Request request) {
        removePreviousData();
        return new Response(OK.get(),
                            headers,
                            getBody(content)).getResponse();
    }

    private void removePreviousData() {
        if (content.size() > 0) {
            content.removeAll(content);
        }
    }

    public String getBody(List<String> content) {
        if (content.size() > 0) {
            return content.get(0);
        } else {
            return "";
        }
    }
}
