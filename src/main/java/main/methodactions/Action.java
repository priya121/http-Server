package main.methodactions;

import main.Request;
import main.responses.DefaultResponse;

public interface Action {
    String getResponse(DefaultResponse response, Request request);
    String getMethod();
}
