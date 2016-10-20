package main.responses;

import main.DefaultHeaders;
import main.request.Request;
import main.Response;

import java.util.List;

import static main.Status.METHOD_NOT_ALLOWED;
import static main.Status.OK;

public class MethodOptionsResponse extends DefaultResponse{
    private final List<String> content;
    private final String methodOptionsHeader;
    private final String defaultHeaders;

    public MethodOptionsResponse(List content) {
        super(content);
        this.content = content;
        this.defaultHeaders = new DefaultHeaders().get();
        this.methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT\n";
    }

    @Override
    public Response get(Request request) {
        return new Response(OK.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response head(Request request) {
        return new Response(OK.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response post(Request request) {
        return new Response(OK.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response put(Request request){
        return new Response(OK.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response options(Request request) {
        return new Response(OK.get(),
                            defaultHeaders + methodOptionsHeader,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response delete(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            convertToBytes(getBody(content)));
    }
}
