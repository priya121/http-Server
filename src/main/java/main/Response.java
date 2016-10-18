package main;

public class Response {
    private byte[] bodyData;
    private String statusLine;
    private String headers;
    private String body;

    public Response(String statusLine, String headers, String body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public Response(String statusLine, String headers, byte[] bodyData) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.bodyData = bodyData;
    }

    public String getHeader() {
        return statusLine + "\n" +
                headers + "\n";
    }

    public byte[] getBody() {
        return bodyData;
    }
}
