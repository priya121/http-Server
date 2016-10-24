package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DefaultHeaders;
import main.response.Response;

import java.util.List;

import static main.Status.OK;

public class TeaResponse extends DefaultResponse {
    private final String defaultHeader;
    private final List content;

    public TeaResponse(List content, Date date) {
        super(content, date);
        this.content = content;
        this.defaultHeader = new DefaultHeaders(date).get();
    }

    @Override
    public Response get(Request request) {
        return new Response(OK.get(),
                            defaultHeader,
                            convertToBytes(getBody(content)));
    }
}
