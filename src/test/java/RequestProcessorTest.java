import main.RequestProcessor;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RequestProcessorTest {
    private BufferedReader reader;
    private String getRequestWithMethodOptions;
    private String simpleGetRequest;
    private String getRequestWithPathFile1;
    private String getRequestWithFoobarPath;
    private String getRequestWithMethodOptions2;

    @Before
    public void setUp() {
        simpleGetRequest = createRequestString("/");
        getRequestWithMethodOptions = createRequestString("/method_options");
        getRequestWithMethodOptions2 = createRequestString("/method_options2");
        getRequestWithPathFile1 = createRequestString("/file1");
        getRequestWithFoobarPath = createRequestString("/foobar");
        reader = createBufferedReader(simpleGetRequest);
    }

    @Test
    public void storesWholeRequest() {
        RequestProcessor processor = new RequestProcessor(reader);
        assertFalse(processor.requestHeaderFields().isEmpty());
    }

    @Test
    public void storesRequestLine() {
        RequestProcessor processor = new RequestProcessor(reader);
        assertThat(processor.requestHeaderFields().get("RequestLine"), is("GET / HTTP/1.1"));
    }

    @Test
    public void requestLineHasPathFoobar() {
        reader = createBufferedReader(getRequestWithFoobarPath);
        RequestProcessor processor = new RequestProcessor(reader);
        assertTrue(processor.requestLineHasPath());
    }

    @Test
    public void requestLineHasPathFile1() {
        reader = createBufferedReader(getRequestWithPathFile1);
        RequestProcessor processor = new RequestProcessor(reader);
        assertTrue(processor.requestLineHasPath());
    }

    @Test
    public void pathForFoobar() {
        reader = createBufferedReader(getRequestWithFoobarPath);
        RequestProcessor processor = new RequestProcessor(reader);
        assertEquals("/foobar", processor.getPath());
    }

    @Test
    public void pathForFile1() {
        reader = createBufferedReader(getRequestWithPathFile1);
        RequestProcessor processor = new RequestProcessor(reader);
        assertEquals("/file1", processor.getPath());
    }

    @Test
    public void noFilePathForEmpty() {
        reader = createBufferedReader(simpleGetRequest);
        RequestProcessor processor = new RequestProcessor(reader);
        assertEquals("No path", processor.getPath());
    }

    @Test
    public void methodOptions() {
        reader = createBufferedReader(getRequestWithMethodOptions);
        RequestProcessor processor = new RequestProcessor(reader);
        assertTrue(processor.methodOptions());
    }

    @Test
    public void methodOptions2() {
        reader = createBufferedReader(getRequestWithMethodOptions2);
        RequestProcessor processor = new RequestProcessor(reader);
        assertTrue(processor.methodOptions2());
    }

    @Test
    public void noMethodOptions2() {
        reader = createBufferedReader(getRequestWithMethodOptions);
        RequestProcessor processor = new RequestProcessor(reader);
        assertFalse(processor.methodOptions2());
    }

    @Test
    public void noMethodOptions() {
        reader = createBufferedReader(simpleGetRequest);
        RequestProcessor processor = new RequestProcessor(reader);
        assertFalse(processor.methodOptions());
    }

    @Test
    public void requestLineHasNoPath() {
        reader = createBufferedReader(simpleGetRequest);
        RequestProcessor processor = new RequestProcessor(reader);
        assertFalse(processor.requestLineHasPath());
    }

    private String createRequestString(String path) {
        return  "GET "+ path + " HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate";
    }

    private BufferedReader createBufferedReader(String request) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(request.getBytes());
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
