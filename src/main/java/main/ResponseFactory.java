package main;

import main.date.RealDate;
import main.request.Request;
import main.responsetypes.*;

import java.io.File;
import java.util.*;

import static main.Method.GET;
import static main.Method.PATCH;

public class ResponseFactory {
    private final HashMap<String, DefaultResponse> responses;
    private final String publicDirectory;
    private final RealDate date;

    public ResponseFactory(String publicDirectory) {
        this.publicDirectory = publicDirectory;
        this.date = new RealDate();
        this.responses = createResponses(publicDirectory);
    }

    private HashMap createResponses(String publicDirectory) {
        HashMap responses = new HashMap<>();
        responses.put("/form", new FormResponse(publicDirectory, date));
        responses.put("/logs", new LogsResponse(publicDirectory, date));
        responses.put("/redirect", new RedirectResponse(date));
        responses.put("/parameters", new ParameterResponse(date));
        responses.put("/cookie?type=chocolate", new CookieResponse(date));
        responses.put("/eat_cookie", new GetCookieResponse(date));
        responses.put("resource", new ResourceResponse(publicDirectory, date));
        responses.put("no resource", new NoResourceResponse(publicDirectory, date));
        return responses;
    }

    public DefaultResponse findRelevantResponse(Request request) {
        if (resourceRequest(request) && !request.getPath().equals("/logs")) {
            return responses.get("resource");
        }
        if (request.getPath().contains("parameters")) {
            return responses.get("/parameters");
        }
        for (Map.Entry<String, DefaultResponse> path : responses.entrySet()) {
            if (path.getKey().equals(request.getPath())) {
               return path.getValue();
            }
        }
        return responses.get("no resource");
    }

    private boolean resourceRequest(Request request) {
        return (requestPossible(request));
    }

    private boolean requestPossible(Request request) {
        return exists(request.getPath()) && isGetOrPatch(request);
    }

    private boolean isGetOrPatch(Request request) {
       return request.getMethod().equals(GET.get()) || request.getMethod().equals(PATCH.get());
    }

    private boolean exists(String filePathToFind) {
        File fileToFind = new File(publicDirectory + filePathToFind);
        return getFiles().stream()
                .filter(fileToFind::equals)
                .findAny()
                .isPresent();
    }

    private List<File> getFiles() {
        File[] files = new File(publicDirectory).listFiles();
        return new ArrayList<>(Arrays.asList(files));
    }
}
