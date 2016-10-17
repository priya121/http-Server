package main;

import main.methodactions.*;
import main.responses.DefaultResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.Method.*;
import static main.Status.METHOD_NOT_ALLOWED;

public class Action {
    List<Action> responseMethods = new ArrayList<Action>() {{(
            add(new Get());
                                            new Post(),
                                            new Put(),
                                            new Head(),
                                            new Options(),
                                            new Delete(),
                                            new BogusRequestMethod());


    public String determine(DefaultResponse response, Request request) {
        String requestMethod = request.getRequestMethod();
        if (requestMethod.equals(GET.get())) {
            return response.get(request);
        } else if (requestMethod.equals(POST.get())) {
            return response.post(request);
        } else if (requestMethod.equals(PUT.get())) {
            return response.put(request);
        } else if (requestMethod.equals(HEAD.get())) {
            return response.head(request);
        } else if (requestMethod.equals(DELETE.get())) {
            return response.delete(request);
        } else if (requestMethod.equals(OPTIONS.get())) {
            return response.options(request);
        }
        return METHOD_NOT_ALLOWED.get() + "\n";
    }
}
