package main.methodactions;

import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;

public class Post implements Action {

    private final String method;

    public Post(String method) {
        this.method = method;
    }

    @Override
    public Response getResponse(DefaultResponse response, Request request) {
        return response.post(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
