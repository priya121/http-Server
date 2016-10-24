package response;

import main.date.Date;
import main.date.TestDate;
import main.request.Request;
import main.response.Response;
import main.responsetypes.FormResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
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
        Date testDate = new TestDate();
        getFormRequest = helper.create("GET /form");
        postFormRequest = helper.create("POST /form");
        putFormRequest= helper.create("PUT /form");
        deleteFormRequest = helper.create("DELETE /form");
        response = new FormResponse(content, testDate);
    }

    @Test
    public void postFormResponse() {
        Response createdResponse = response.get(postFormRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void putFormResponse() {
        Response createdResponse = response.get(putFormRequest);
        assertThat(createdResponse.getStatusLine(), is("HTTP/1.1 200 OK\n"));
    }

    @Test
    public void postFormAddsDataToContent() {
        response.post(putFormRequest);
        byte[] body = response.get(getFormRequest).getBody();
        assertThat(convertToString(body), containsString("\ndata=fatcat"));
    }

    @Test
    public void putFormOverwritesPreviousData() {
        content = new ArrayList<>(Collections.singletonList("data=fatcat"));
        response.put(putFormRequest);
        byte[] body = response.get(getFormRequest).getBody();
        assertFalse(convertToString(body).contains("\ndata=fatcat"));
        assertTrue(convertToString(body).contains("\ndata=heathcliff"));
    }

    @Test
    public void deleteRemovesData() {
        content = new ArrayList<>(Collections.singletonList("data=heathcliff"));
        response.delete(deleteFormRequest);
        assertFalse(convertToString(response.get(getFormRequest).getBody()).equals("\ndata=heathcliff"));
    }

    private String convertToString(byte[] body) {
        return new String(body);
    }
}
