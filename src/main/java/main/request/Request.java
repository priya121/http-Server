package main.request;

import main.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private final BufferedReader reader;
    private final RequestLine requestLine;
    private HashMap<String, String> headerFields = new HashMap<>();

    public Request(BufferedReader reader) {
        this.reader = reader;
        this.requestLine = setRequestLine();
        this.headerFields = setHeaderFields();
    }

    public String getRequestLine() {
        return requestLine.getMethodType() +
                " " + requestLine.getPath() +
                " " + requestLine.getProtocol();
    }

    public String getRequestMethod() {
        return requestLine.getMethodType();
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public Map<String, String> getHeaders() {
        return headerFields;
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

    private HashMap<String, String> setHeaderFields() {
        String line;
        try {
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] split = line.split(":", 2);
                headerFields.put(split[0], split[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return headerFields;
    }

    public String getBody() {
        String body = "";
        if (headerFields.containsKey("Content-Length")) {
            int size = Integer.parseInt(headerFields.get("Content-Length"));
            for (int i = 0; i < size; i++) {
                try {
                    body += ((char) reader.read());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }
        return body;
    }
}
