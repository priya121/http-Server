package main.responses;

import main.Range;
import main.Response;
import main.request.Request;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static main.Status.*;

public class ResourceResponse extends DefaultResponse {
    private final String publicDirectory;
    private String header;

    public ResourceResponse(String publicDirectory, List content) {
        super(content);
        this.publicDirectory = publicDirectory;
        this.header = "Date: \n" +
                      "Content-Length: \n";
    }

    @Override
    public Response get(Request request) {
        if (request.getPath().contains("/partial_content.txt")) {
            String byteRange = request.getHeaders().get("Range");

            int beforeEquals = 3;
            String bytes = byteRange.substring(byteRange.length() - beforeEquals, byteRange.length());
            Range range = new Range(bytes);

            return new Response(PARTIAL.get(),
                    header += findMediaType(request),
                    range.getRange(requestBody(request), bytes));
        }

        return new Response(OK.get(),
                            header += findMediaType(request),
                            requestBody(request));
    }

    @Override
    public Response patch(Request request) {

        File fileToPatch = new File(publicDirectory + request.getPath());
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(Files.readAllBytes(Paths.get(publicDirectory + request.getPath())));
            byte[] digest = messageDigest.digest();
            String hex = DatatypeConverter.printHexBinary(digest).toLowerCase();
            updateIfMatch(fileToPatch, request, hex);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (NoSuchAlgorithmException e) {
        }
        return new Response(NO_CONTENT.get(),
                            header + "Etag: " + request.getHeaders().get("If-Match") + "\n",
                            requestBody(request));
    }

    private void updateIfMatch(File fileToPatch, Request request, String hex) {
        if (request.getHeaders().get("If-Match").equals(hex)) {
            try {
                overWriteFileContents(fileToPatch, request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private void overWriteFileContents(File file, Request request) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(request.getBody());
        writer.close();
    }
}
