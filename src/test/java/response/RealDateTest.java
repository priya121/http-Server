package response;

import main.date.Date;
import main.date.RealDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RealDateTest {

    @Test
    public void dateFormattedCorrectly() {
       Date date = new RealDate();
       assertEquals(30, date.getDate().length());
    }
}
