package main.methodactions;

import main.Request;
import main.responses.DefaultResponse;

public class Get implements Action {
    private final String method;

    public Get(String method) {
        this.method = method;
    }

    public String getResponse(DefaultResponse response, Request request) {
        return response.get(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
