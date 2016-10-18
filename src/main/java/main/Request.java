package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

public class Request {
    private final BufferedReader reader;
    private final RequestLine requestLine;
    public HashMap<String, String> headerFields = new HashMap<>();

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

    public String getHeaderFields() {
        String content = "";
        for (Map.Entry<String, String> entry: headerFields.entrySet()) {
            content += entry.getKey() + ": " + entry.getValue() + "\n";
        }
        return content;
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
        try {
            List<String> headers = Arrays.asList("Host", "Connection", "User-Agent", "Accept-Encoding");
            for (String headerFieldName : headers) {
                String line = reader.readLine();
                addsRangeIfPresent(headers, line);
                addHeader(headerFieldName, line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return headerFields;
    }

    private void addsRangeIfPresent(List<String> headers, String line) {
        if (line.contains("Range")) {
            headers.set(0, "Range");
            addHeader("Range", line);
        }
    }

    private void addHeader(String headerFieldName, String line) {
        int patternLimit = 2;
        String[] headerFieldSplit = line.split(":", patternLimit);
        headerFields.put(headerFieldName, headerFieldSplit[1].trim());
    }
}
