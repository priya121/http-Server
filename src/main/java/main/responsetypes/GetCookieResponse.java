package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DateHeader;
import main.response.Response;

import static main.Status.OK;

public class GetCookieResponse extends DefaultResponse {

    private final String headers;

    public GetCookieResponse(Date date) {
        super(date);
        this.headers = new DateHeader(date).get();
    }

    @Override
    public Response get(Request request) {
        if (request.getHeaders().get("Cookie").equals("type=chocolate")) {
            return new Response(OK.get(),
                    headers,
                    convertToBytes("mmmm chocolate"));
        } else {
            return new Response(OK.get(),
                    headers,
                    convertToBytes(""));
        }
    }
}
