package main;

public class Response {
    private String statusLine;
    private String headers;
    private String body;

    public Response(String statusLine, String headers, String body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public String getResponse() {
        return statusLine +"\n" +
               headers + "\n" +
               body + "\n";
    }
}
