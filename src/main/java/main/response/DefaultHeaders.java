package main.response;

import main.date.Date;

public class DefaultHeaders {

    private final Date date;

    public DefaultHeaders(Date date) {
        this.date = date;
    }

    public String get() {
        return date.getDate() + "\n" +
               "Content-Length: \n";
    }
}
