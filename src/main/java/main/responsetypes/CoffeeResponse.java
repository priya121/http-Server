package main.responsetypes;

import main.DefaultHeaders;
import main.response.Response;
import main.Status;
import main.request.Request;

import java.util.List;

public class CoffeeResponse extends DefaultResponse {
    private final String defaultHeaders;
    private final List content;

    public CoffeeResponse(List content) {
        super(content);
        this.content = content;
        this.defaultHeaders = new DefaultHeaders().get();
    }

    @Override
    public Response get(Request request) {
        content.add("\n<h1> I'm a teapot</h1>");
        return new Response(Status.COFFEE.get(), "\n" +
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }
}
