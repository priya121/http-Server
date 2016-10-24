package main.request;

public class RequestBody {

    private final String body;

    public RequestBody(String body) {
        this.body = body;
    }

    public String get() {
        return body;
    }
}
