package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DateHeader;
import main.response.Response;

import static main.Method.PATCH;
import static main.Status.METHOD_NOT_ALLOWED;

public class DefaultResponse {
    private final String defaultHeaders;

    public DefaultResponse(Date date) {
        this.defaultHeaders = new DateHeader(date).get();
    }

    public Response get(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(""));
    }

    public Response head(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(""));
    }

    public Response delete(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(""));
    }

    public Response post(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(""));
    }

    public Response put(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(""));
    }

    public Response options(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(""));
    }

    public Response patch(Request request) {
        return new Response(PATCH.get(),
                            defaultHeaders,
                            convertToBytes(""));
    }


    public byte[] convertToBytes(String content) {
        return content.getBytes();
    }

}
