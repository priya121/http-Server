package main.response;

import main.date.Date;

public class DateHeader {

    private final Date date;

    public DateHeader(Date date) {
        this.date = date;
    }

    public String get() {
        return date.getDate() + "\n";

    }
}
