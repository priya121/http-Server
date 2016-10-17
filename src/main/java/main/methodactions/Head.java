package main.methodactions;

import main.Request;
import main.responses.DefaultResponse;

public class Head implements Action {
    private final String method;

    public Head(String method) {
        this.method = method;
    }

    @Override
    public String getResponse(DefaultResponse response, Request request) {
        return response.head(request);
    }

    public String getMethod() {
        return method;
    }
}
