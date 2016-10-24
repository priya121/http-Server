package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DefaultHeaders;
import main.response.Response;

import java.util.List;

import static main.Status.OK;

public class MethodOptions2Response extends DefaultResponse {
    private final String methodOptionsHeader;
    private final String defaultHeaders;
    private final List content;

    public MethodOptions2Response(List content, Date date) {
        super(content, date);
        this.content = content;
        this.defaultHeaders = new DefaultHeaders(date).get();
        this.methodOptionsHeader = "Allow: GET,OPTIONS\n";
    }

    @Override
    public Response get(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response options(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            convertToBytes(getBody(content)));
    }
}
