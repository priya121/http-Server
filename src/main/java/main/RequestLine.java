package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class RequestLine {
    private final String method;
    private final String path;
    private final ArrayList validMethods;

    public RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
        this.validMethods = new ArrayList<>(Arrays.asList(Method.GET.get(), Method.PUT.get(), Method.POST.get(), Method.HEAD.get(), Method.OPTIONS.get()));
    }

    public Optional getMethodType() {
        Optional methods = validMethods.stream()
               .filter(validMethod -> validMethod.equals(method))
               .findAny();
        return methods;
    }

    public String getPath() {
        return path;
    }
}
