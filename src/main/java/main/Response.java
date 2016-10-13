package main;

public class Response {
    private final String status200;
    private final String status404;
    private final String status418;
    private final String allowHeaderForMethodOptions;
    private final String allowHeaderForMethodOptions2;
    private String status405;

    public Response() {
        this.status200 = "HTTP/1.1 200 OK\n";
        this.status404 = "HTTP/1.1 404 Not Found\n";
        this.status418 = "HTTP/1.1 418 I'm a teapot\n";
        this.status405 = "HTTP/1.1 405 ";
        this.allowHeaderForMethodOptions = "Allow: GET,HEAD,POST,OPTIONS,PUT\n";
        this.allowHeaderForMethodOptions2 = "Allow: GET,OPTIONS\n";
    }

    public String determineStatusLine(Request request) {
        if (request.validRequestMethod() && !request.getPath().equals("/foobar") && !request.getPath().equals("/coffee")) {
            return status200;
        } else if (request.getPath().equals("/coffee")) {
            return status418;
        } else if (!request.validRequestMethod()) {
            return status405;
        }
        else return status404;
    }

    public String determineStatusLinesForMethodOptions(Request request) {
        if (request.methodOptions()) {
            return allowHeaderForMethodOptions;
        } else if (request.methodOptions2()) {
            return allowHeaderForMethodOptions2;
        }
        return "";
    }

    public String determineBody(Request request) {
        if (request.coffee()) {
            return "\n<h1> I'm a teapot</h1>\n";
        }
        return "";
    }

    public String get(Request request) {
        return determineStatusLine(request) +
               determineStatusLinesForMethodOptions(request) +
               determineBody(request);
    }
}
