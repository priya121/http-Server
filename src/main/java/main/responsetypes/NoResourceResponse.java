package main.responsetypes;

import main.date.Date;
import main.response.DefaultHeaders;
import main.request.Request;
import main.response.Response;

import java.util.List;

import static main.Status.NOT_FOUND;

public class NoResourceResponse extends DefaultResponse {
    private final List<String> content;
    private final String headers;

    public NoResourceResponse(List content, Date date) {
        super(content, date);
        this.content = content;
        this.headers = new DefaultHeaders(date).get();
    }

    @Override
    public Response get(Request request) {
        return new Response(NOT_FOUND.get(),
                headers,
                convertToBytes(getBody(content)));
    }

    @Override
    public Response head(Request request) {
        return new Response(NOT_FOUND.get(),
                headers,
                convertToBytes(getBody(content)));
    }

    @Override
    public Response delete(Request request) {
        return new Response(NOT_FOUND.get(),
                headers,
                convertToBytes(getBody(content)));
    }
}
