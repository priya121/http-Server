package main.methodactions;

import main.Request;
import main.responses.DefaultResponse;

public class Options implements Action {

    private final String method;

    public Options(String method) {
        this.method = method;
    }

    @Override
    public String getResponse(DefaultResponse response, Request request) {
        return response.options(request);
    }

    @Override
    public String getMethod() {
        return method;
    }
}
