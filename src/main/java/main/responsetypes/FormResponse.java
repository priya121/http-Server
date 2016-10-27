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

import static main.Status.OK;

public class FormResponse extends DefaultResponse {
    private final String publicDirectory;
    private final String headers;

    public FormResponse(String publicDirectory, Date date) {
        super(date);
        this.publicDirectory = publicDirectory;
        this.headers = new DateHeader(date).get();
    }

    @Override
    public Response get(Request request) {
        try {
            createFormFile(request);
            return new Response(OK.get(),
                    headers,
                    requestBody(request));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Response post(Request request) {
        try {
            File createFile = new File(publicDirectory + request.getPath());
            overWriteFileContents(createFile, "\ndata=fatcat");
            return new Response(OK.get(),
                    headers,
                    requestBody(request));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Response put(Request request) {
        try {
            File createFile = new File(publicDirectory + request.getPath());
            overWriteFileContents(createFile, "\ndata=heathcliff");
            return new Response(OK.get(),
                    headers,
                    requestBody(request));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Response delete(Request request) {
        try {
            File createFile = new File(publicDirectory + request.getPath());
            overWriteFileContents(createFile, "");
            return new Response(OK.get(),
                    headers,
                    requestBody(request));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
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

    private void overWriteFileContents(File file, String data) throws IOException {
        FileWriter writer = new FileWriter(file, false);
        writer.write(data);
        writer.close();
    }

    private void createFormFile(Request request) throws IOException {
        File createFile = new File(publicDirectory + request.getPath());
        createFile.createNewFile();
    }
}
