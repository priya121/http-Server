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
        this.header = "";
    }

    @Override
    public Response get(Request request) {
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
