package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DefaultHeaders;
import main.response.Response;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static main.Status.METHOD_NOT_ALLOWED;
import static main.Status.OK;

public class EmptyPathResponse extends DefaultResponse {
    private final List<String> content;
    private final String headers;
    private final String publicDirectory;

    public EmptyPathResponse(List content, String publicDirectory, Date date) {
        super(content, date);
        this.content = content;
        this.publicDirectory = publicDirectory;
        this.headers = new DefaultHeaders(date).get();
    }

    @Override
    public Response get(Request request) {
        content.add(allFileLinks());
        return new Response(OK.get(),
                            headers,
                            convertToBytes(getBody(content)));
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

    private String allFileLinks() {
        String display = "";
        for (File file : getFiles()) {
            String fileName = file.getPath().substring(file.getPath().lastIndexOf("/"));
            display += "<a href=" + fileName + ">" + fileName + "</a>\n" + "\n";
        }
        return display;
    }

    private List<File> getFiles() {
        File[] files = new File(publicDirectory).listFiles();
        return Arrays.asList(files);
    }
}
