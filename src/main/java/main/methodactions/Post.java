package main.methodactions;

import main.Request;
import main.responses.DefaultResponse;

public class Post implements Action {

    private final String method;

    public Post(String method) {
        this.method = method;
    }

    @Override
    public String getResponse(DefaultResponse response, Request request) {
        return response.post(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
