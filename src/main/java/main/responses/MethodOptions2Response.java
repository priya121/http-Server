package main.responses;

import main.request.Request;
import main.Response;

import java.util.List;

import static main.Status.OK;

public class MethodOptions2Response extends DefaultResponse {
    private final String methodOptionsHeader;
    private final String defaultHeaders;
    private final List content;

    public MethodOptions2Response(List content) {
        super(content);
        this.content = content;
        this.defaultHeaders = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                              "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                              "ETag: \n" +
                              "Accept-Ranges: none\n" +
                              "Content-Length: \n" +
                              "Connection: close\n" +
                              "Content-Type: \n";
        this.methodOptionsHeader = "Allow: GET,OPTIONS\n";
    }

    @Override
    public Response get(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response options(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            convertToBytes(getBody(content)));
    }
}
