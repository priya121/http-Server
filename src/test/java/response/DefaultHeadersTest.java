package response;

import main.date.Date;
import responsetypes.TestDate;
import main.response.DateHeader;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DefaultHeadersTest {

    private DateHeader headers;

    @Test
    public void recordsDateGiven() {
        Date testDate = new TestDate();
        headers = new DateHeader(testDate);
        assertThat(headers.get(), containsString("Date: Sun, 18 Oct 2009 08:56:53 GMT"));
    }

    @Test
    public void getsDefaultHeaders() {
        Date testDate = new TestDate();
        headers = new DateHeader(testDate);
        assertEquals("Date: Sun, 18 Oct 2009 08:56:53 GMT\n", headers.get());
    }
}
