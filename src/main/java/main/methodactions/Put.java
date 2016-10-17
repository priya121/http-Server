package main.methodactions;

import main.Request;
import main.responses.DefaultResponse;

public class Put implements Action {

    private final String method;

    public Put(String method) {
        this.method = method;
    }

    @Override
    public String getResponse(DefaultResponse response, Request request) {
        return response.put(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
