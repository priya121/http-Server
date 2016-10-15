package main;

import java.util.HashMap;

public class ResponseFactory {

    private final HashMap<String, ResponseType> responses;

    public ResponseFactory() {
        responses = new HashMap<>();
    }

    public HashMap addResponses(String path, ResponseType response) {
        responses.put(path, response);
        return responses;
    }

}
