package main;

import main.responses.*;

import java.util.*;

public class ResponseFactory {
    private HashMap<String, DefaultResponse> responses;
    private List validPaths = new ArrayList(Arrays.asList());

    public ResponseFactory(List content) {
        this.responses = new HashMap<>();
        responses.put("/", new EmptyPathResponse(content));
        responses.put("/form", new FormResponse(content));
        responses.put("/method_options", new MethodOptionsResponse(content));
        responses.put("/method_options2", new MethodOptions2Response(content));
        responses.put("/coffee", new CoffeeResponse(content));
        responses.put("/tea", new TeaResponse(content));
        responses.put("/redirect", new RedirectResponse(content));
        responses.put("no resource", new NoResourceResponse(content));
        responses.put("default", new DefaultResponse(content));
    }

    public DefaultResponse findRelevantResponse(Request request) {
        for (Map.Entry<String, DefaultResponse> path : responses.entrySet()) {
            if (path.getKey().equals(request.getPath())) {
               return path.getValue();
            }
        }
        return responses.get("no resource");
    }
}
