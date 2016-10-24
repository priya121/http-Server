package main.request;

import main.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;

public class Request {
    private final BufferedReader reader;
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private final HashMap headers = new HashMap<String, String>();

    public Request(BufferedReader reader) {
        this.reader = reader;
        this.requestLine = setRequestLine();
        this.requestHeader = setHeaderFields();
    }

    public String getRequestLine() {
        return requestLine.getMethodType() +
                " " + requestLine.getPath() +
                " " + requestLine.getProtocol();
    }

    public String getMethod() {
        return requestLine.getMethodType();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HashMap getHeaders() {
        return requestHeader.getHeaders();
    }

    public String getHeader(String header) {
        return requestHeader.getHeader(header);
    }

    public RequestBody setBody() {
        String body = "";
        if (requestHeader.getHeaders().containsKey("Content-Length")) {
            int size = Integer.parseInt(requestHeader.getHeader("Content-Length"));
            for (int i = 0; i < size; i++) {
                try {
                    body += ((char) reader.read());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }
        return new RequestBody(body);
    }

    private RequestLine setRequestLine() {
        try {
            String requestLine = reader.readLine();
            String[] requestSpilt = requestLine.split(" ");
            return new RequestLine(requestSpilt[0],
                    requestSpilt[1],
                    new Protocol(requestSpilt[2]));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private RequestHeader setHeaderFields() {
        try {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] split = line.split(":", 2);
                headers.put(split[0], split[1].trim());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return new RequestHeader(headers);
    }
}
