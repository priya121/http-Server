package main.response;

public class Response {
    private byte[] bodyData;
    private String statusLine;
    private String headers;

    public Response(String statusLine, String headers, byte[] bodyData) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.bodyData = bodyData;
    }

    public String getStatusLine() {
        return statusLine + "\n";
    }

    public String getHeader() {
        return headers +
               new ContentLengthHeader(bodyData).get() +"\n\n";
    }

    public byte[] getBody() {
        return bodyData;
    }
}
