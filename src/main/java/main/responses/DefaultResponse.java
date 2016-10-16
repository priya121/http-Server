package main.responses;

import main.FilePathList;
import main.Request;
import main.Response;

import java.util.List;

import static main.Status.METHOD_NOT_ALLOWED;
import static main.Status.OK;

public class DefaultResponse {
    private final FilePathList fileList;
    private List<String> content;
    private final String defaultHeaders;

    public DefaultResponse(List content) {
        fileList = new FilePathList();
        this.content = content;
        this.defaultHeaders = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                              "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                              "ETag: \n" +
                              "Accept-Ranges: none\n" +
                              "Content-Length: \n" +
                              "Connection: close\n" +
                              "Content-Type: text/plain\n";
    }

    public String get(Request request) {
        if (request.validRequestMethod() && fileList.validFilePath(request.getPath())) {
            return new Response(OK.get(),
                    defaultHeaders,
                    getBody(content)).getResponse();
        } else {
            return new Response(METHOD_NOT_ALLOWED.get(),
                    defaultHeaders,
                    getBody(content)).getResponse();
        }
    }

    public String head(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            "").getResponse();
    }

    public String delete(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            "").getResponse();

    }

    public String post(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            "").getResponse();
    }

    public String put(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            "").getResponse();
    }

    public String options(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            defaultHeaders,
                            "").getResponse();
    }

    public String getBody(List<String> content) {
        if (content.size() > 0) {
            return content.get(0);
        } else {
            return "";
        }
    }
}
