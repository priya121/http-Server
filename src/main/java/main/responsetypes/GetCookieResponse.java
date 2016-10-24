package main.responsetypes;

import main.DefaultHeaders;
import main.request.Request;
import main.response.Response;

import java.util.List;

import static main.Status.OK;

public class GetCookieResponse extends DefaultResponse {

    private final String headers;
    private List content;

    public GetCookieResponse(List content) {
        super(content);
        this.content = content;
        this.headers = new DefaultHeaders().get();
    }

    @Override
    public Response get(Request request) {
        if (request.getHeaders().get("Cookie").equals("type=chocolate")) {
            content.add("mmmm chocolate");
            return new Response(OK.get(),
                                headers,
                                convertToBytes(getBody(content)));
        } else {
            return new Response(OK.get(),
                                headers,
                                convertToBytes(getBody(content)));
        }
    }
}
