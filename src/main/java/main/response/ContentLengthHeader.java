package main.response;

public class ContentLengthHeader {

    private final byte[] content;

    public ContentLengthHeader(byte[] content) {
        this.content = content;
    }

    public String get() {
        return "Content-Length: " + content.length;
    }
}
