package response;

import main.responses.ParameterResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParameterResponseTest {

    @Test
    public void canTellIfThereAreParametersPresent() {
        List<String> content = new ArrayList<>();
        ParameterResponse response = new ParameterResponse(content);

    }
}
