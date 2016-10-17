package main;

import main.methodactions.*;
import main.responses.DefaultResponse;

import java.util.Arrays;
import java.util.List;

import static main.Method.*;
import static main.Status.METHOD_NOT_ALLOWED;

public class ActionChooser {
    private final List<Action> responseMethods = Arrays.asList(new Get(GET.get()),
                                                               new Post(POST.get()),
                                                               new Put(PUT.get()),
                                                               new Head(HEAD.get()),
                                                               new Options(OPTIONS.get()),
                                                               new Delete(DELETE.get()));

    public String determine(DefaultResponse response, Request request) {
        String requestMethod = request.getRequestMethod();
        for (Action action : responseMethods) {
            if (action.getMethod().equals(requestMethod)) {
                return action.getResponse(response, request);
            }
        }
        return METHOD_NOT_ALLOWED.get() + "\n";
    }
}
