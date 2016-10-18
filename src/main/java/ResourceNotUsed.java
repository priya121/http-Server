import main.Request;
import main.Response;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.Method.GET;
import static main.Status.OK;

public class ResourceNotUsed {
    private final String publicDirectory;
    private final String filePathToFind;

    public ResourceNotUsed(String publicDirectory, Request request) {
        this.publicDirectory = publicDirectory;
        this.filePathToFind = request.getPath();
    }

    public Response get() {
        return new Response(OK.get(),
                            "",
                            requestBody());
    }

    public boolean requestPossible(Request request) {
        return exists() && request.getRequestMethod().equals(GET.get());
    }

    private boolean exists() {
        File fileToFind = new File(publicDirectory + filePathToFind);
        return getFiles().stream()
                         .filter(fileToFind::equals)
                         .findAny()
                         .isPresent();
    }

    public List<File> getFiles() {
        File[] files = new File(publicDirectory).listFiles();
        return new ArrayList<>(Arrays.asList(files));
    }

    public byte[] requestBody() {
        Path path = Paths.get(publicDirectory + filePathToFind);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
