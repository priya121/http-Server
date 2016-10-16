package main.responses;

import main.Request;
import main.Response;

import java.util.List;

import static main.Status.OK;

public class MethodOptions2Response extends DefaultResponse {
    private final String methodOptionsHeader;
    private final String defaultHeaders;

    public MethodOptions2Response(List content) {
        super(content);
        this.defaultHeaders = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                              "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                              "ETag: \n" +
                              "Accept-Ranges: none\n" +
                              "Content-Length: \n" +
                              "Connection: close\n" +
                              "Content-Type: text/plain\n";
        this.methodOptionsHeader = "Allow: GET,OPTIONS\n";
    }

    @Override
    public String get(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            "").getResponse();
    }

    @Override
    public String options(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            "").getResponse();
    }
}
