package main.responsetypes;

import main.date.Date;
import main.request.Request;
import main.response.DateHeader;
import main.response.Response;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static main.Status.NOT_AUTHORIZED;
import static main.Status.OK;

public class LogsResponse extends DefaultResponse {

    private String headers;
    private final String publicDirectory;
    private final String WWWAuthenticate = "WWW-Authenticate: Basic realm=\"My Server\"";

    public LogsResponse(String publicDirectory, Date date) {
        super(date);
        this.headers = new DateHeader(date).get();
        this.publicDirectory = publicDirectory;
    }

    @Override
    public Response get(Request request) {
        if (hasAuthorization(request)) {
            try {
                File file = new File(publicDirectory + "/logs");
                logRequest(file, request);
                return new Response(OK.get(),
                                    headers,
                                    requestBody(request));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return new Response(NOT_AUTHORIZED.get(),
                            headers += WWWAuthenticate,
                            convertToBytes(""));
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

    private void logRequest(File file, Request request) throws IOException {
        FileWriter writer = new FileWriter(file, true);
        writer.write(request.getRequestLine());
        writer.close();
    }
}
