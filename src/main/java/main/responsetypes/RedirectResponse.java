package main.responsetypes;

import main.date.Date;
import main.response.DefaultHeaders;
import main.request.Request;
import main.response.Response;

import java.util.List;

import static main.Status.REDIRECT;

public class RedirectResponse extends DefaultResponse {
    private final List content;
    private String headers;
    private String redirectLocation = "Location: http://localhost:5000/\n";

    public RedirectResponse(List content, Date date) {
        super(content, date);
        this.content = content;
        this.headers = new DefaultHeaders(date).get();
    }

    @Override
    public Response get(Request request) {
        return new Response(REDIRECT.get(),
                            headers += redirectLocation,
                            convertToBytes(getBody(content)));
    }
}
