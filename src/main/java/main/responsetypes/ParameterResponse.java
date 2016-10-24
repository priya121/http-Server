package main.responsetypes;

import main.DefaultHeaders;
import main.response.Response;
import main.request.Request;

import java.net.URLDecoder;
import java.util.List;

import static main.Status.OK;

public class ParameterResponse extends DefaultResponse {

    private final String headers;

    public ParameterResponse(List content) {
        super(content);
        this.headers = new DefaultHeaders().get();
    }

    @Override
    public Response get(Request request) {
        return new Response(OK.get(),
                            headers,
                            convertToBytes(decode(request)));
    }

    public String decode(Request request) {
        String[] parameters = getParameters(request);

        String variableDecoded = getFirstVariable(parameters[0]);

        if (parameters.length > 1) {
            return getSecondVariable(parameters, variableDecoded);
        }
        return variableDecoded;
    }

    private String[] getParameters(Request request) {
        String[] removeStartOfPath = request.getPath().split("\\?");
        return removeStartOfPath[1].split("\\&");
    }

    private String getFirstVariable(String firstVariable) {
        String variable = URLDecoder.decode(firstVariable);
        return variable.replaceFirst("=", " = ");
    }

    private String getSecondVariable(String[] secondHalf, String variableDecoded) {
        String variable2;
        String variable2Decoded;
        variable2 = URLDecoder.decode(secondHalf[1]);
        variable2Decoded = variable2.replace("=", " = ");
        return variableDecoded + variable2Decoded;
    }

}
