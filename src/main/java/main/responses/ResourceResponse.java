package main.responses;

import main.Range;
import main.Response;
import main.request.Request;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                      "Date: \n" +
                      "Server: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                      "ETag: \n" +
                      "Accept-Ranges: none\n" +
                      "Content-Length: \n" +
                      "Connection: close\n";
    }

    @Override
    public Response get(Request request) {
        if (request.getPath().contains("/partial_content.txt")) {
            String byteRange = request.getHeaders().get("Range");
            int lastThree = 3;
            String bytes = byteRange.substring(byteRange.length() - lastThree, byteRange.length());
            Range range = new Range(bytes);
            byte[] range1 = range.getRange(requestBody(request), bytes);

            return new Response(PARTIAL.get(),
                    header += findMediaType(request),
                    range1);
        }
        return new Response(OK.get(),
                            header += findMediaType(request),
                            requestBody(request));
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
