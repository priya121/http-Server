import main.RequestLine;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RequestLineTest {

    @Test
    public void hasAMethodType() {
        RequestLine requestLine = new RequestLine("GET", "/foobar");
        assertEquals(Optional.of("GET"), requestLine.getMethodType());
        assertEquals("/foobar", requestLine.getPath());
    }

    @Test
    public void determinesIfThereIsAPath() {
        RequestLine requestLine = new RequestLine("PUT", "/file1");
        assertEquals(Optional.of("PUT"), requestLine.getMethodType());
        assertEquals("/file1", requestLine.getPath());
    }
}
