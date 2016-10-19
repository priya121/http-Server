package main.methodactions;

import main.request.Request;
import main.Response;
import main.responses.DefaultResponse;

public interface Action {
    Response getResponse(DefaultResponse response, Request request);
    String getMethod();
}
