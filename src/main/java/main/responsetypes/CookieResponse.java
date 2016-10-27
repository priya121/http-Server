package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DateHeader;
import main.response.Response;

import static main.Status.OK;

public class CookieResponse extends DefaultResponse {

    private String headers;

    public CookieResponse(Date date) {
        super(date);
        this.headers = new DateHeader(date).get();
    }

    @Override
    public Response get(Request request) {
        String[] cookie = request.getPath().split("\\?");
        String[] type = cookie[1].split("=");
        if (type[1].equals("chocolate")) {
            return new Response(OK.get(),
                                headers += "Set-Cookie: type=" + type[1] +
                                "\n", convertToBytes("Eat"));
        } else {
            return new Response(OK.get(),
                                headers += "Set-Cookie: type=" + type[1] +
                                "\n", convertToBytes(""));
        }
    }
}
