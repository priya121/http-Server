package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DefaultHeaders;
import main.response.Response;

import java.util.List;

import static main.Status.OK;

public class CookieResponse extends DefaultResponse {

    private final List content;
    private String headers;

    public CookieResponse(List content, Date date) {
        super(content, date);
        this.content = content;
        this.headers = new DefaultHeaders(date).get();
    }

    @Override
    public Response get(Request request) {
        String[] cookie = request.getPath().split("\\?");
        String[] type = cookie[1].split("=");
        if (type[1].equals("chocolate")) {
            content.add("Eat");
            return new Response(OK.get(),
                                headers += "Set-Cookie: type=" + type[1] +
                                "\n", convertToBytes(getBody(content)));
        } else {
            return new Response(OK.get(),
                                headers += "Set-Cookie: type=" + type[1] +
                                "\n", convertToBytes(getBody(content)));
        }
    }
}
