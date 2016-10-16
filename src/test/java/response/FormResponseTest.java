package response;

import main.Request;
import main.responses.FormResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class FormResponseTest {
    private ArrayList<String> content = new ArrayList<>();
    private final TestHelper helper = new TestHelper();
    private Request getFormRequest;
    private Request postFormRequest;
    private Request putFormRequest;
    private Request deleteFormRequest;
    private FormResponse response;

    @Before
    public void setUp() {
        getFormRequest = helper.create("GET /form");
        postFormRequest = helper.create("POST /form");
        putFormRequest= helper.create("PUT /form");
        deleteFormRequest = helper.create("DELETE /form");
        response = new FormResponse(content);
    }

    @Test
    public void postFormResponse() {
        String createdResponse = response.get(postFormRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void putFormResponse() {
        String createdResponse = response.get(putFormRequest);
        assertThat(createdResponse, containsString("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void postFormAddsDataToContent() {
        FormResponse formResponse = new FormResponse(content);
        formResponse.post(putFormRequest);
        assertThat(formResponse.get(getFormRequest), containsString("\ndata=fatcat"));
    }

    @Test
    public void putFormOverwritesPreviousData() {
        content = new ArrayList<>(Collections.singletonList("data=fatcat"));
        FormResponse formResponse = new FormResponse(content);
        formResponse.put(putFormRequest);
        assertFalse(formResponse.get(getFormRequest).contains("\ndata=fatcat"));
        assertTrue(formResponse.get(getFormRequest).contains("\ndata=heathcliff"));
    }

    @Test
    public void deleteRemovesData() {
        content = new ArrayList<>(Collections.singletonList("data=heathcliff"));
        FormResponse formResponse = new FormResponse(content);
        formResponse.delete(deleteFormRequest);
        assertFalse(formResponse.get(getFormRequest).contains("\ndata=heathcliff"));
    }
}
