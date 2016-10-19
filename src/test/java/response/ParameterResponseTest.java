package response;

import main.request.Request;
import main.responses.ParameterResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class ParameterResponseTest {
    private final TestHelper helper = new TestHelper();
    private ParameterResponse response;
    private Request getParameters;
    private Request getOtherParameters;
    private Request getAllParameters;

    @Before
    public void setUp() {
        getParameters = helper.create("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C");
        getOtherParameters= helper.create("GET /parameters?variable_1%3DOperators%20%40%2C%20%23%2C%20%24&variable_2%3Dstuff");
        getAllParameters = helper.create("GET //parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C" +
                                         "%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%" +
                                         "20all%22%3F&variable_2=stuff");
    }

    @Test
    public void canTellIfThereAreParametersPresent() {
        List<String> content = new ArrayList<>();
        ParameterResponse response = new ParameterResponse(content);
        assertThat(new String(response.get(getParameters).getBody()), containsString("variable_1 = Operators <, >,"));
    }

    @Test
    public void canFindOtherParameters() {
        List<String> content = new ArrayList<>();
        ParameterResponse response = new ParameterResponse(content);
        assertThat(new String(response.get(getOtherParameters).getBody()), containsString("variable_1 = Operators @, #, $" +
                                                                                          "variable_2 = stuff"));
    }

    @Test
    public void getFirstVariable() {
        List<String> content = new ArrayList<>();
        ParameterResponse response = new ParameterResponse(content);
        assertThat(new String(response.get(getOtherParameters).getBody()), containsString("variable_1 = Operators @, #, $"));
    }

    @Test
    public void getsSecondVariable() {
        List<String> content = new ArrayList<>();
        ParameterResponse response = new ParameterResponse(content);
        assertThat(new String(response.get(getOtherParameters).getBody()), containsString("variable_2 = stuff"));
    }

    @Test
    public void getsAllParameters() {
        List<String> content = new ArrayList<>();
        ParameterResponse response = new ParameterResponse(content);
        String bodyString = new String(response.get(getAllParameters).getBody());
        assertThat(bodyString, containsString("variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?"));
        assertThat(bodyString, containsString("variable_2 = stuff"));
    }
}
