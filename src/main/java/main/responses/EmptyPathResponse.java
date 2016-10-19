package main.responses;

import main.request.Request;
import main.Response;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static main.Status.METHOD_NOT_ALLOWED;
import static main.Status.OK;

public class EmptyPathResponse extends DefaultResponse {
    private final List<String> content;
    private final String headers;
    private final String publicDirectory;

    public EmptyPathResponse(List content, String publicDirectory) {
        super(content);
        this.content = content;
        this.publicDirectory = publicDirectory;
        this.headers = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                       "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                       "ETag: \n" +
                       "Accept-Ranges: none\n" +
                       "Content-Length: \n" +
                       "Connection: close\n" +
                       "Content-Type: text/plain\n";
    }

    @Override
    public Response get(Request request) {
        List bodyWithLinks = Arrays.asList(allFileLinks());
        return new Response(OK.get(),
                            headers,
                            convertToBytes(getBody(bodyWithLinks)));
    }

    @Override
    public Response post(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                headers,
                convertToBytes(getBody(content)));
    }

    @Override
    public Response head(Request request) {
        return new Response(OK.get(),
                            headers ,
                            convertToBytes(getBody(content)));
    }


    public String allFileLinks() {
        String display = "";
        for (File file : getFiles()) {
            String fileName = file.getPath().substring(file.getPath().lastIndexOf("/"));
            display += "<a href=" + fileName + ">" + fileName + "</a>\n" + "\n";
        }
        return display;
    }

    public List<File> getFiles() {
        File[] files = new File(publicDirectory).listFiles();
        return Arrays.asList(files);
    }
}
