package main.responses;

import main.DefaultHeaders;
import main.request.Request;
import main.Response;

import java.util.List;

import static main.Method.PATCH;
import static main.Status.METHOD_NOT_ALLOWED;

public class DefaultResponse {
    private List<String> content;
    private final String defaultHeaders;

    public DefaultResponse(List content) {
        this.content = content;
        this.defaultHeaders = new DefaultHeaders().get();
    }

    public Response get(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    public Response head(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    public Response delete(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));

    }

    public Response post(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    public Response put(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    public Response options(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    public String getBody(List<String> content) {
        if (content.size() > 0) {
            return content.get(0);
        } else {
            return "";
        }
    }

    public byte[] convertToBytes(String content) {
        return content.getBytes();
    }

    public Response patch(Request request) {
        return new Response(PATCH.get(), defaultHeaders, convertToBytes(getBody(content)));
    }

    public Response get_cookie(Request request) {
        return new Response(PATCH.get(), defaultHeaders, convertToBytes(getBody(content)));
    }
}
