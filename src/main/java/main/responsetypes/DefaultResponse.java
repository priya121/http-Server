package main.responsetypes;

import main.date.Date;
import main.response.DefaultHeaders;
import main.response.Response;
import main.request.Request;

import java.util.List;

import static main.Method.PATCH;
import static main.Status.METHOD_NOT_ALLOWED;

public class DefaultResponse {
    private List<String> content;
    private final String defaultHeaders;

    public DefaultResponse(List content, Date date) {
        this.content = content;
        this.defaultHeaders = new DefaultHeaders(date).get();
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

    public Response patch(Request request) {
        return new Response(PATCH.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    public String getBody(List<String> content) {
        String body = "";
        for (String item : content) {
            body += item;
        }
        return body;
    }

    public byte[] convertToBytes(String content) {
        return content.getBytes();
    }

}
