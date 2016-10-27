package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DateHeader;
import main.response.Response;

import static main.Status.REDIRECT;

public class RedirectResponse extends DefaultResponse {
    private String headers;
    private String redirectLocation = "Location: http://localhost:5000/\n";

    public RedirectResponse(Date date) {
        super(date);
        this.headers = new DateHeader(date).get();
    }

    @Override
    public Response get(Request request) {
        return new Response(REDIRECT.get(),
                            headers += redirectLocation,
                            convertToBytes(""));
    }
}
