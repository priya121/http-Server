package main.methodactions;

import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;

public class Delete implements Action {

    private final String method;

    public Delete(String method) {
        this.method = method;
    }

    @Override
    public Response getResponse(DefaultResponse response, Request request) {
        return response.delete(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
