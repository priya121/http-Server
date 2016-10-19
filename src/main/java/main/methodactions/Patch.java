package main.methodactions;

import main.Request;
import main.Response;
import main.responses.DefaultResponse;

public class Patch implements Action {

    private final String method;

    public Patch(String method) {
        this.method = method;
    }

    @Override
    public Response getResponse(DefaultResponse response, Request request) {
        return response.patch(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
