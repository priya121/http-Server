package main.methodactions;

import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;

public class Options implements Action {

    private final String method;

    public Options(String method) {
        this.method = method;
    }

    @Override
    public Response getResponse(DefaultResponse response, Request request) {
        return response.options(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
