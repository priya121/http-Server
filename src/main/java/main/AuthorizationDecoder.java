package main;

import java.util.Base64;

public class AuthorizationDecoder {

    public String decode(String code) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(code));
    }
}
