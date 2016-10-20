package main;

public enum Method {
    GET("GET"),
    PUT("PUT"),
    POST("POST"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private String method;

    Method (String method) {
        this.method = method;
    }

    public String get() {
        return method;
    }
}
