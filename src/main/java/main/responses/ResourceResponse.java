package main.responses;

import main.Request;
import main.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static main.Status.OK;

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
        if (request.getPath().equals("/text-file.txt")) {
            header += "Content-Type: text/plain\n";
        } else {
            header += "Content-Type: " + request.getPath().replace(".", "/").substring(1) + "\n";
        }
        return new Response(OK.get(),
                            header,
                            requestBody(request));
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
