package main.responses;

import main.Request;
import main.Response;
import main.Status;

import java.util.List;

public class MethodOptions2Response extends DefaultResponse {
    private final List<String> content;
    private final String methodOptionsHeader;
    private final String headers;

    public MethodOptions2Response(List content) {
        super(content);
        this.content = content;
        this.headers = "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                       "Server:Apache-HttpClient/4.3.5 (java 1.5)\n" +
                       "ETag: \n" +
                       "Accept-Ranges: none\n" +
                       "Content-Length: \n" +
                       "Connection: close\n" +
                       "Content-Type: text/plain\n";
        this.methodOptionsHeader = "Allow: GET,OPTIONS\n";
    }

    @Override
    public String options(Request request) {
        return new Response(ok200(), headers + methodOptionsHeader, "").getResponse(request);
    }

    public String ok200() {
        return Status.OK.get();
    }

    public String getBody(List<String> content) {
        if (content.size() > 0) {
            return content.get(0);
        } else {
            return "";
        }
    }
}
