package main.responses;

import main.Response;
import main.request.Request;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static main.Status.NOT_AUTHORIZED;
import static main.Status.OK;

public class LogsResponse extends DefaultResponse {
    private final String publicDirectory;
    private String headers;

    public LogsResponse(String publicDirectory, List content) {
        super(content);
        this.headers = "";
        this.publicDirectory = publicDirectory;
    }

    @Override
    public Response get(Request request) {
        if (request.getHeaders().containsKey("Authorization") &&
           (isAuthorized(request.getHeaders().get("Authorization").split(" ")[1]))) {
            File file = new File(publicDirectory + "/logs");
            try {
                overWriteFileContents(file, request);
                return new Response(OK.get(), this.headers, requestBody(request));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        this.headers = "WWW-Authenticate: Basic realm=\"My Server\"";
        return new Response(NOT_AUTHORIZED.get(), this.headers, "".getBytes());
    }

    @Override
    public Response head(Request request) {
        if (request.getHeaders().containsKey("Authorization") &&
            isAuthorized(request.getHeaders().get("Authorization").split(" ")[1])) {
            System.out.print(true);
            File file = new File(publicDirectory + "/logs");
            try {
                overWriteFileContents(file, request);
                return new Response(OK.get(), headers, "HEAD /requests HTTP/1.1".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Response(NOT_AUTHORIZED.get(), "", "".getBytes());
    }

    private boolean isAuthorized(String authorization) {
        return authorization.equals("YWRtaW46aHVudGVyMg==");
    }

    private byte[] requestBody(Request request) {
        try {
            Path path = Paths.get(publicDirectory + request.getPath());
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void overWriteFileContents(File file, Request request) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        writer.write(request.getRequestLine());
        writer.close();
    }
}
