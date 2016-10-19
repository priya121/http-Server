package main.request;

import main.Protocol;

public class RequestLine {
    private final String method;
    private final String path;
    private final Protocol protocol;

    public RequestLine(String method, String path, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
    }

    public String getMethodType() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol.get();
    }
}
