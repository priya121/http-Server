package main.request;

import java.util.HashMap;

public class RequestHeader {
    private final HashMap<String, String> headers;

    public RequestHeader(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public HashMap getHeaders() {
        return headers;
    }

    public String getHeader(String header) {
        return headers.get(header);
    }
}
