package main.responsetypes;

import main.DefaultHeaders;
import main.response.Response;
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

    private String headers;
    private final String publicDirectory;
    private final List content;
    private final String WWWAuthenticate = "WWW-Authenticate: Basic realm=\"My Server\"";

    public LogsResponse(String publicDirectory, List content) {
        super(content);
        this.content = content;
        this.headers = new DefaultHeaders().get();
        this.publicDirectory = publicDirectory;
    }

    @Override
    public Response get(Request request) {
        if (hasAuthorization(request)) {
            try {
                File file = new File(publicDirectory + "/logs");
                overWriteFileContents(file, request);
                return new Response(OK.get(),
                                    headers,
                                    requestBody(request));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return new Response(NOT_AUTHORIZED.get(),
                            headers += WWWAuthenticate,
                            convertToBytes(getBody(content)));
    }

    private boolean hasAuthorization(Request request) {
        return request.getHeaders().containsKey("Authorization") &&
               (isAuthorized(getAuth(request.getHeader("Authorization").split(" ")[1])));
    }

    private String getAuth(String authorization) {
        return authorization;
    }

    private boolean isAuthorized(String authorization) {
        return authorization.equals("YWRtaW46aHVudGVyMg==");
    }

    private byte[] requestBody(Request request) throws IOException {
        Path path = Paths.get(publicDirectory + request.getPath());
        return Files.readAllBytes(path);
    }

    private void overWriteFileContents(File file, Request request) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        writer.write(request.getRequestLine());
        writer.close();
    }
}
