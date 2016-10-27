package main.date;

import java.text.SimpleDateFormat;

public class RealDate implements Date{

    @Override
    public String getDate() {
        return new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z").format(new java.util.Date().getTime());
    }
}
