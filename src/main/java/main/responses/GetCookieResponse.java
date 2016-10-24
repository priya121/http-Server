package main.responses;

import main.Response;
import main.request.Request;

import java.util.List;

import static main.Status.OK;

public class GetCookieResponse extends DefaultResponse {

    public GetCookieResponse(List content) {
        super(content);
    }

    @Override
    public Response get(Request request) {
        if (request.getHeaders().get("Cookie").equals("type=chocolate")) {
            return new Response(OK.get(), "", "mmmm chocolate".getBytes());
        } else {
            return new Response(OK.get(), "", "".getBytes());
        }
    }
}
