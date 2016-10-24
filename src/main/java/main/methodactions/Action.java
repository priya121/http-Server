package main.methodactions;

import main.request.Request;
import main.response.Response;
import main.responsetypes.DefaultResponse;

public interface Action {
    Response getResponse(DefaultResponse response, Request request);
    String getMethod();
}
