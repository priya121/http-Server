package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Set;

public class RequestProcessor {
    private final BufferedReader reader;
    private final HashMap<String, String> requestFields = new HashMap<>();
    private final String[] headerFieldNames = {"Host", "Connection", "User-Agent", "Accept-Encoding"};

    public RequestProcessor(BufferedReader reader) {
        this.reader = reader;
        setRequestLine();
        setRequestHeaderFields();
    }

    public HashMap<String, String> requestFields() {
        return requestFields;
    }

    public boolean requestLineHasPath() {
        String[] req = requestFields.get("RequestLine").split(" ");
        return req[1].length() > 1;
    }

    public String getRequestMethod() {
        String[] split = requestFields.get("RequestLine").split(" ");
        return split[0];
    }

    public String getPath() {
        if (requestLineHasPath()) {
            String[] req = requestFields.get("RequestLine").split(" ");
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

    private void setRequestLine() {
        try {
            String requestLine = reader.readLine();
            requestFields.put("RequestLine", requestLine);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void setRequestHeaderFields() {
        try {
            for (String headerFieldName : headerFieldNames) {
                String line = reader.readLine();
                formatField(headerFieldName, line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getHeaderFields() {
        String content = "";
        Set<String> headerFields = requestFields().keySet();
        headerFields.remove("RequestLine");
        for (String entry: headerFields) {
            content += entry += ": " + requestFields().get(entry) + "\n";
        }
        return content;
    }

    private void formatField(String headerFieldName, String line) {
        int patternLimit = 2;
        String[] headerFieldSplit = line.split(":", patternLimit);
        requestFields.put(headerFieldName, headerFieldSplit[1].trim());
    }

}
