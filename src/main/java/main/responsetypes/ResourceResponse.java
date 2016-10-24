package main.responsetypes;

import main.date.Date;
import main.response.DefaultHeaders;
import main.Range;
import main.response.Response;
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

    public ResourceResponse(String publicDirectory, List content, Date date) {
        super(content, date);
        this.publicDirectory = publicDirectory;
        this.header =  new DefaultHeaders(date).get();
    }

    @Override
    public Response get(Request request) {
        if (request.getPath().equals("/partial_content.txt")) {
            Range range = new Range(getByteRange(request));

            return new Response(PARTIAL.get(),
                                header += findMediaType(request),
                                range.get(requestBody(request)));
        }
        return new Response(OK.get(),
                            header = findMediaType(request),
                            requestBody(request));
    }

    @Override
    public Response patch(Request request) {
        try {
            File fileToPatch = new File(publicDirectory + request.getPath());
            String hex = createHex(request);
            updateIfMatch(fileToPatch, request, hex);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new Response(NO_CONTENT.get(),
                            header + "Etag: " + request.getHeader("If-Match") + "\n",
                            requestBody(request));
    }


    private String getByteRange(Request request) {
        String byteRange = request.getHeader("Range");
        return byteRange.substring(byteRange.length() - 3, byteRange.length());
    }

    private void updateIfMatch(File fileToPatch, Request request, String hex) throws IOException {
        if (request.getHeaders().get("If-Match").equals(hex)) {
            overWriteFileContents(fileToPatch, request);
        }
    }

    private String createHex(Request request) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.update(Files.readAllBytes(Paths.get(publicDirectory + request.getPath())));
        byte[] digest = messageDigest.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
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

    private byte[] requestBody(Request request) {
        try {
            Path path = Paths.get(publicDirectory + request.getPath());
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void overWriteFileContents(File file, Request request) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(request.setBody().get());
        writer.close();
    }
}
