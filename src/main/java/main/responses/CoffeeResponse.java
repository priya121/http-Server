package main.responses;

import main.Request;
import main.Response;
import main.Status;

import java.util.List;

public class CoffeeResponse extends DefaultResponse {
    private final String defaultHeaders;

    public CoffeeResponse(List content) {
        super(content);
        this.defaultHeaders = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                              "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                              "ETag: \n" +
                              "Accept-Ranges: none\n" +
                              "Content-Length \n" +
                              "Connection: close\n" +
                              "Content-Type: text/plain\n";
    }

    @Override
    public Response get(Request request) {
        return new Response(Status.COFFEE.get(),
                            "\n" + defaultHeaders,
                            convertToBytes("\n\n<h1> I'm a teapot</h1>"));
    }
}
