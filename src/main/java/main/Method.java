package main;

public enum Method {
    GET("GET"),
    PUT("PUT"),
    POST("POST"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS");

    private String method;

    Method (String symbol) {
        this.method = symbol;
    }

    public String get() {
        return method;
    }
}
