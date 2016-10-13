package main;

import java.io.BufferedReader;
import java.util.*;

public class GetRequest {
        private final BufferedReader reader;
        private final RequestProcessor processor;
        private final ArrayList validMethods;

        public GetRequest(BufferedReader reader) {
            this.reader = reader;
            this.processor = new RequestProcessor(reader);
            this.validMethods = new ArrayList<>(Arrays.asList(Method.GET.get(), Method.PUT.get(), Method.POST.get(), Method.HEAD.get(), Method.OPTIONS.get()));
        }

        public String getRequestLine() {
            return processor.requestFields().get("main.RequestLine");
        }

        public String getMethod() {
            return processor.getRequestMethod();
        }

        public String getHeaderFields() {
            return processor.getHeaderFields();
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
