import main.request.Request;
import main.ResponseFactory;
import main.responses.*;
import org.junit.Before;
import org.junit.Test;
import response.TestHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ResponseFactoryTest {
    private final TestHelper helper = new TestHelper();
    private List content;
    private Request emptyGetRequest;
    private Request postFormRequest;
    private Request getCoffeeRequest;
    private Request getTeaRequest;
    private Request redirectRequest;
    private Request methodOptionsRequest;
    private Request methodOptions2Request;
    private Request getFoobar;
    private String publicDirectory;

    @Before
    public void setUp() {
        content = new ArrayList<>();
        publicDirectory = "/Users/priyapatil/cob_spec/public";
        emptyGetRequest = helper.create("GET /");
        postFormRequest = helper.create("POST /form");
        getCoffeeRequest = helper.create("GET /coffee");
        getTeaRequest = helper.create("GET /tea");
        redirectRequest = helper.create("GET /redirect");
        methodOptionsRequest = helper.create("GET /method_options");
        methodOptions2Request = helper.create("GET /method_options2");
        getFoobar = helper.create("GET /foobar");
    }

    @Test
    public void returnsEmptyPathResponse() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(emptyGetRequest);
        assertTrue(response instanceof EmptyPathResponse);
    }

    @Test
    public void getsFormResponse() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(postFormRequest);
        assertTrue(response instanceof FormResponse);
    }

    @Test
    public void getsCoffeeResponse() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getCoffeeRequest);
        assertTrue(response instanceof CoffeeResponse);
    }

    @Test
    public void getsTeaResponse() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getTeaRequest);
        assertTrue(response instanceof TeaResponse);
    }

    @Test
    public void getsRedirectResponse() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(redirectRequest);
        assertTrue(response instanceof RedirectResponse);
    }

    @Test
    public void getsMethodOptionsResponse() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(methodOptionsRequest);
        assertTrue(response instanceof MethodOptionsResponse);
    }

    @Test
    public void getsMethodOptions2Response() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(methodOptions2Request);
        assertTrue(response instanceof MethodOptions2Response);
    }

    @Test
    public void returnsNoResourceResponseIfNoMatchFound() {
        ResponseFactory responses = new ResponseFactory(content, publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getFoobar);
        assertTrue(response instanceof NoResourceResponse);
    }
}
