package responsetypes;

import main.date.Date;

public class TestDate implements Date {

    @Override
    public String getDate() {
        return "Date: Sun, 18 Oct 2009 08:56:53 GMT";
    }
}
