package main.responses;

import main.DefaultHeaders;
import main.request.Request;
import main.Response;

import java.util.List;

import static main.Status.NOT_FOUND;

public class NoResourceResponse extends DefaultResponse {
    private final List<String> content;
    private final String headers;

    public NoResourceResponse(List content) {
        super(content);
        this.content = content;
        this.headers = new DefaultHeaders().get();
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
