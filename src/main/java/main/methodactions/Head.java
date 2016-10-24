package main.methodactions;

import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;

public class Head implements Action {

    private final String method;

    public Head(String method) {
        this.method = method;
    }

    @Override
    public Response getResponse(DefaultResponse response, Request request) {
        return response.head(request);
    }

    public String getMethod() {
        return method;
    }
}
