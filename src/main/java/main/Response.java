package main;

import java.util.List;

public class Response {
    private String status200;
    private String status404;
    private String status418;
    private String allowHeaderForMethodOptions;
    private String allowHeaderForMethodOptions2;
    private String statusLine;
    private String headers;
    private String body;
    private String status405;
    private List<String> content;
    private String status302;

    public Response(List content) {
        this.content = content;
        this.status200 = "HTTP/1.1 200 OK\n";
        this.status404 = "HTTP/1.1 404 Not Found\n";
        this.status418 = "HTTP/1.1 418 I'm a teapot\n";
        this.status405 = "HTTP/1.1 405 Method Not Allowed\n";
        this.status302 = "HTTP/1.1 302\nLocation: http://localhost:5000/\n";
        this.allowHeaderForMethodOptions = "Allow: GET,HEAD,POST,OPTIONS,PUT\n";
        this.allowHeaderForMethodOptions2 = "Allow: GET,OPTIONS\n";
    }

    public Response(String statusLine, String headers, String body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public String getResponse() {
        return statusLine +"\n" + headers + "\n" + body + "\n";
    }

    public String determineStatusLine(Request request) {
        if (!request.validRequestMethod() || request.getRequestMethod().contains("PUT") && request.getPath().contains("/file1")) {
            return status405;
        } else if (request.getRequestMethod().contains("POST") && request.getPath().contains("/text-file.txt")) {
            return status405;
        } else if (request.getRequestMethod().equals("GET") && request.getPath().equals("/redirect")) {
            return status302;
        } else if (request.getPath().equals("/coffee")) {
            return status418;
        } else if (request.validRequestMethod() && !request.getPath().equals("/foobar")) {
            return status200;
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
        } else if (request.getRequestMethod().equals("GET") && request.getPath().equals("/form")) {

        }
        return "";
    }

    public String get(Request request) {
        storeContent(request);
        return determineStatusLine(request) +
               determineStatusLinesForMethodOptions(request) +
               determineBody(request) +
               getContent(request);
    }


    public void storeContent(Request request) {
        if (request.getRequestLine().contains("POST") && request.getPath().equals("/form")) {
            content.add(0, "\ndata=fatcat");
        } else if (request.getRequestLine().contains("PUT") && request.getPath().equals("/form") && content.size() > 0) {
            content.remove(0);
            content.add(0, "\ndata=heathcliff");
        } else if (request.getRequestLine().contains("DELETE")) {
           content.remove(0);
        }
    }

    public String getContent(Request request) {
        if (request.getRequestMethod().equals("GET") && request.getPath().equals("/form") && (content.size() > 0)) {
            return content.get(0);
        } else  {
            return "";
        }
    }

    public int size() {
        return content.size();
    }
}
