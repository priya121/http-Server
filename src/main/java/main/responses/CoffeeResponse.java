package main.responses;

import main.DefaultHeaders;
import main.request.Request;
import main.Response;
import main.Status;

import java.util.List;

public class CoffeeResponse extends DefaultResponse {
    private final String defaultHeaders;

    public CoffeeResponse(List content) {
        super(content);
        this.defaultHeaders = new DefaultHeaders().get();
    }

    @Override
    public Response get(Request request) {
        return new Response(Status.COFFEE.get(),
                            "\n" + defaultHeaders,
                            convertToBytes("\n\n<h1> I'm a teapot</h1>"));
    }
}
