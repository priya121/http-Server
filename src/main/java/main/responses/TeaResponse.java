package main.responses;

import main.DefaultHeaders;
import main.request.Request;
import main.Response;

import java.util.List;

import static main.Status.OK;

public class TeaResponse extends DefaultResponse {
    private final String defaultHeader;
    private final List content;

    public TeaResponse(List content) {
        super(content);
        this.content = content;
        this.defaultHeader = new DefaultHeaders().get();
    }

    @Override
    public Response get(Request request) {
        return new Response(OK.get(),
                            defaultHeader,
                            convertToBytes(getBody(content)));
    }
}
