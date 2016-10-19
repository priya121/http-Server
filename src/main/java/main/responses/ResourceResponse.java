package main.responses;

import main.Request;
import main.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static main.Status.OK;
import static main.Status.PARTIAL;

public class ResourceResponse extends DefaultResponse {
    private final String publicDirectory;
    private String header;

    public ResourceResponse(String publicDirectory, List content) {
        super(content);
        this.publicDirectory = publicDirectory;
        this.header = "Location: http://localhost:5000/\n" +
                      "Date: Sun, 18 Oct 2009 08:56:53 GMT\n" +
                      "Server: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                      "ETag: \n" +
                      "Accept-Ranges: none\n" +
                      "Content-Length: \n" +
                      "Connection: close\n";
    }

    @Override
    public Response get(Request request) {
        if (request.getPath().contains("partial")) {
            String range = request.headerFields.get("Range");
            return new Response(PARTIAL.get(), "", getRange(requestBody(request), range));
        } else {
            return new Response(OK.get(),
                    header += findMediaType(request),
                    requestBody(request));
        }
    }

    @Override
    public Response patch(Request request) {
        return new Response(OK.get(),
                header += findMediaType(request),
                requestBody(request));
    }

    public byte[] getRange(byte[] body, String byteRange) {
        String bytes = byteRange.substring(byteRange.length() - 3, byteRange.length());
        String[] range = bytes.split("");
        if (bytes.startsWith("=-")) {
            return bytesFromEnd(body, bytes);
        }
        if (bytes.endsWith("-")) {
            return bytesFromBeginning(body, range[1]);
        }
        int beginningValue = Integer.valueOf(range[0]);
        int endValue = Integer.valueOf(range[2]);
        return Arrays.copyOfRange(body, beginningValue, endValue + 1);
    }

    private byte[] bytesFromBeginning(byte[] body, String s) {
        int val = Integer.valueOf(s);
        return Arrays.copyOfRange(body, val, body.length);
    }

    private byte[] bytesFromEnd(byte[] body, String bytes) {
        int endValue = Integer.valueOf(bytes.substring(bytes.length() - 1, bytes.length()));
        return Arrays.copyOfRange(body, body.length - endValue, body.length);
    }

    private String findMediaType(Request request) {
        if (request.getPath().equals("/text-file.txt")) {
            return "Content-Type: text/plain\n";
        } else {
            return "Content-Type: " + request.getPath()
                                              .replace(".", "/")
                                              .substring(1) + "\n";
        }
    }

    public byte[] requestBody(Request request) {
        Path path = Paths.get(publicDirectory + request.getPath());
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
