package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;

public class RequestProcessor {
    private final BufferedReader reader;
    private final HashMap<String, String> requestHeaderFields = new HashMap<>();
    private final String[] headerFieldNames = {"Host", "Connection", "User-Agent", "Accept-Encoding"};

    public RequestProcessor(BufferedReader reader) {
        this.reader = reader;
        setRequestLine();
        setRequestHeaderFields();
    }

    private void setRequestLine() {
        try {
            String requestLine = reader.readLine();
            requestHeaderFields.put("RequestLine", requestLine);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void setRequestHeaderFields() {
        try {
            for (int i = 0; i < headerFieldNames.length; i++) {
                String line = reader.readLine();
                setRequestLine(headerFieldNames[i], line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void setRequestLine(String headerFieldName, String line) {
        int patternLimit = 2;
        String[] headerFieldSplit = line.split(":", patternLimit);
        requestHeaderFields.put(headerFieldName, headerFieldSplit[1].trim());

    }

    public HashMap<String, String> requestHeaderFields() {
        return requestHeaderFields;
    }

    public boolean requestLineHasPath() {
        String[] req = requestHeaderFields.get("RequestLine").split(" ");
        return req[1].length() > 1;
    }

    public String getPath() {
        if (requestLineHasPath()) {
            String[] req = requestHeaderFields.get("RequestLine").split(" ");
            return req[1];
        }
        return "No path";
    }

    public boolean methodOptions() {
        return getPath().equals("/method_options");
    }

    public boolean methodOptions2() {
        return getPath().equals("/method_options2");
    }

    public boolean coffee() {
        return getPath().equals("/coffee");
    }
}
