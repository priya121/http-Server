package response;

import main.request.Request;
import main.ResponseFactory;
import main.responsetypes.*;
import org.junit.Before;
import org.junit.Test;
import responsetypes.TestHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ResponseFactoryTest {
    private final TestHelper helper = new TestHelper();
    private List content;
    private Request postFormRequest;
    private Request redirectRequest;
    private Request getFoobar;
    private String publicDirectory;
    private Request getParameters;
    private Request getFile1;
    private Request getEatCookie;
    private Request getCookie;

    @Before
    public void setUp() {
        content = new ArrayList<>();
        publicDirectory = "/Users/priyapatil/cob_spec/public";
        getFile1 = helper.create("GET /file1");
        postFormRequest = helper.create("POST /form");
        redirectRequest = helper.create("GET /redirect");
        getFoobar = helper.create("GET /foobar");
        getCookie = helper.create("GET /cookie?type=chocolate");
        getEatCookie = helper.create("GET /eat_cookie");
        getParameters = helper.create("GET /parameters?variable_1=Operators%20%3C%");
    }

    @Test
    public void getsFormResponse() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(postFormRequest);
        assertTrue(response instanceof FormResponse);
    }

    @Test
    public void getsRedirectResponse() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(redirectRequest);
        assertTrue(response instanceof RedirectResponse);
    }

    @Test
    public void returnsResourceResponseIfFound() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getFile1);
        assertTrue(response instanceof ResourceResponse);
    }

    @Test
    public void returnsNoResourceResponseIfNoMatchFound() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getFoobar);
        assertTrue(response instanceof NoResourceResponse);
    }

    @Test
    public void getsParameterResponse() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getParameters);
        assertTrue(response instanceof ParameterResponse);
    }

    @Test
    public void getsLogs() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getCookie);
        assertTrue(response instanceof CookieResponse);
    }

    @Test
    public void getsCookieResponse() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getCookie);
        assertTrue(response instanceof CookieResponse);
    }

    @Test
    public void getsEatCookieResponse() {
        ResponseFactory responses = new ResponseFactory(publicDirectory);
        DefaultResponse response = responses.findRelevantResponse(getEatCookie);
        assertTrue(response instanceof GetCookieResponse);
    }
}
