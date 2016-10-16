package main.responses;

import main.Request;
import main.Response;

import java.util.List;

import static main.Status.OK;

public class TeaResponse extends DefaultResponse {
    private final List content;
    private final String defaultHeader;

    public TeaResponse(List content) {
        super(content);
        this.content = content;
        this.defaultHeader = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
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
                            defaultHeader,
                            "").getResponse();
    }
}
