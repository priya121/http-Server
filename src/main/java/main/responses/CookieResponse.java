package main.responses;

import main.DefaultHeaders;
import main.Response;
import main.request.Request;

import java.util.List;

import static main.Status.OK;

public class CookieResponse extends DefaultResponse {

    private final List content;
    private final String headers;

    public CookieResponse(List content) {
        super(content);
        this.content = content;
        this.headers = new DefaultHeaders().get();
    }

    @Override
    public Response get(Request request) {
        String[] cookie = request.getPath().split("\\?");
        String[] split = cookie[1].split("=");
        if (split[1].equals("chocolate")) {
            return new Response(OK.get(), "Set-Cookie: type=" + split[1] + "\n", "Eat".getBytes());
        } else {
            return new Response(OK.get(), "Set-Cookie: type=" + split[1] + "\n", "".getBytes());
        }
    }

    @Override
    public Response get_cookie(Request request) {
        return new Response(OK.get(), "", "mmmm chocolate".getBytes());
    }
}
