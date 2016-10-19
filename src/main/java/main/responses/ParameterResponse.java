package main.responses;

import java.util.List;

public class ParameterResponse extends DefaultResponse {

    private final List content;

    public ParameterResponse(List content) {
        super(content);
        this.content = content;
    }
}
