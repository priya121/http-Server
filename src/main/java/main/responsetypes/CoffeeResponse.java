package main.responsetypes;

import main.Status;
import main.date.Date;
import main.request.Request;
import main.response.DefaultHeaders;
import main.response.Response;

import java.util.List;

public class CoffeeResponse extends DefaultResponse {
    private final String defaultHeaders;
    private final List content;

    public CoffeeResponse(List content, Date date) {
        super(content, date);
        this.content = content;
        this.defaultHeaders = new DefaultHeaders(date).get();
    }

    @Override
    public Response get(Request request) {
        content.add("\n<h1> I'm a teapot</h1>");
        return new Response(Status.COFFEE.get(), "\n" +
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }
}
