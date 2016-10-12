package main;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Request {
    private final BufferedReader reader;
    private final RequestProcessor processor;
    private final ArrayList validMethods;

    public Request(BufferedReader reader) {
        this.reader = reader;
        this.processor = new RequestProcessor(reader);
        this.validMethods = new ArrayList<>(Arrays.asList(Method.GET.get(), Method.PUT.get(), Method.POST.get(), Method.HEAD.get(), Method.OPTIONS.get()));
    }

    public String getRequestLine() {
        return processor.requestHeaderFields().get("RequestLine");
    }

    public String getMethod() {
        String[] split = getRequestLine().split(" ");
        return split[0];
    }

    public String getHost() {
        return processor.requestHeaderFields().get("Host");
    }

    public String getConnection() {
        return processor.requestHeaderFields().get("Connection");
    }

    public String getPath() {
        return processor.getPath();
    }

    public boolean methodOptions() {
        return processor.methodOptions();
    }

    public boolean methodOptions2() {
        return processor.methodOptions2();
    }

    public boolean validRequestMethod() {
        return validMethods.stream()
                            .filter(method -> method.equals(getMethod()))
                            .findAny()
                            .isPresent();
    }

    public boolean coffee() {
        return processor.coffee();
    }
}
