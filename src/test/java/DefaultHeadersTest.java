import main.DefaultHeaders;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultHeadersTest {

    @Test
    public void getsDefaultHeaders() {
        DefaultHeaders headers = new DefaultHeaders();
        assertEquals("Date: \n" +
                     "Content-Length: \n" +
                     "Content-Type: \n", headers.get());
    }
}
