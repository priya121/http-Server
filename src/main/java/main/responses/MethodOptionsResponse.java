package main.responses;

import main.Request;
import main.Response;

import java.util.List;

import static main.Status.METHOD_NOT_ALLOWED;
import static main.Status.OK;

public class MethodOptionsResponse extends DefaultResponse{
    private final List<String> content;
    private final String methodOptionsHeader;
    private final String defaultHeaders;

    public MethodOptionsResponse(List content) {
        super(content);
        this.content = content;
        this.defaultHeaders = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                      "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                      "ETag: \n" +
                      "Accept-Ranges: none\n" +
                      "Content-Length: \n" +
                      "Connection: close\n" +
                      "Content-Type: text/plain\n";
        this.methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT\n";
    }

    @Override
    public String get(Request request) {
        return new Response(OK.get(),
                            defaultHeaders,
                            getBody(content)).getResponse();
    }

    @Override
    public String head(Request request) {
        return new Response(OK.get(),
                            defaultHeaders,
                            "").getResponse();
    }

    @Override
    public String post(Request request) {
        return new Response(OK.get(),
                            defaultHeaders,
                            "").getResponse();
    }

    @Override
    public String put(Request request){
        return new Response(OK.get(),
                            defaultHeaders,
                            "").getResponse();
    }

    @Override
    public String options(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            "").getResponse();
    }

    @Override
    public String delete(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            "").getResponse();
    }
}
