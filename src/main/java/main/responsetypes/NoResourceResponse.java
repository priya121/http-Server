package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DateHeader;
import main.response.Response;

import java.io.File;
import java.util.*;

import static main.Status.*;

public class NoResourceResponse extends DefaultResponse {
    private final String headers;
    private final Map<String, Response> possibleGetResponses;
    private final List<String> content;
    private final String publicDirectory;
    private final String methodOptions2Header;
    private final String methodOptionsHeader;

    public NoResourceResponse(String publicDirectory, Date date) {
        super(date);
        this.publicDirectory = publicDirectory;
        this.headers = new DateHeader(date).get();
        this.content = new ArrayList<>();
        this.methodOptionsHeader = "Allow: GET,HEAD,POST,OPTIONS,PUT\n";
        this.methodOptions2Header = "Allow: GET,OPTIONS\n";
        this.possibleGetResponses = createGetResponses();
    }

    @Override
    public Response get(Request request) {
        for (String key : possibleGetResponses.keySet()) {
            if (key.equals(request.getPath())) {
                return possibleGetResponses.get(request.getPath());
            }
        }
        return new Response(NOT_FOUND.get(),
                headers,
                convertToBytes(""));
    }

    @Override
    public Response head(Request request) {
        if (request.getPath().equals("/") || request.getPath().equals("/method_options")) {
            return new Response(OK.get(),
                                headers ,
                                convertToBytes(""));
        }
        return new Response(NOT_FOUND.get(),
                            headers,
                            convertToBytes(""));
    }

    @Override
    public Response delete(Request request) {
        return new Response(NOT_FOUND.get(),
                            headers,
                            convertToBytes(""));
    }

    @Override
    public Response post(Request request) {
        return new Response(METHOD_NOT_ALLOWED.get(),
                            headers,
                            convertToBytes(getBody(content)));
    }

    @Override
    public Response options(Request request) {
        if (request.getPath().equals("/")) {
            return new Response(METHOD_NOT_ALLOWED.get(),
                    headers ,
                    convertToBytes(""));
        }
        for (String key : possibleGetResponses.keySet()) {
            if (key.equals(request.getPath())) {
                return possibleGetResponses.get(request.getPath());
            }
        }
        return new Response(METHOD_NOT_ALLOWED.get(), headers, convertToBytes(""));
    }

    private HashMap<String, Response> createGetResponses() {
        content.add(allFileLinks());
        return new HashMap<String, Response>() {{
            put("/coffee", new Response(COFFEE.get(), headers, convertToBytes("\n<h1> I'm a teapot <h1>")));
            put("/tea", new Response(OK.get(), headers, convertToBytes("")));
            put("/method_options", new Response(OK.get(), headers + methodOptionsHeader, convertToBytes("")));
            put("/method_options2", new Response(OK.get(), headers + methodOptions2Header, convertToBytes("")));
            put("/", new Response(OK.get(), headers, convertToBytes(getBody(content))));
        }};
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

    private String getBody(List<String> content) {
        String body = "";
        for (String item : content) {
            body += item;
        }
        return body;
    }
}
