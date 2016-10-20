package main.responses;

import main.DefaultHeaders;
import main.request.Request;
import main.Response;

import java.util.List;

import static main.Status.REDIRECT;

public class RedirectResponse extends DefaultResponse {
    private final List content;
    private final String headers;

    public RedirectResponse(List content) {
        super(content);
        this.content = content;
        this.headers = "Location: http://localhost:5000/\n" +
                        new DefaultHeaders().get();
    }

    @Override
    public Response get(Request request) {
        return new Response(REDIRECT.get(),
                            headers,
                            convertToBytes(getBody(content)));
    }
}
