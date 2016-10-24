import main.AuthorizationDecoder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthorizationDecoderTest {

    @Test
    public void translatesCode() {
        AuthorizationDecoder decoder = new AuthorizationDecoder();
        String usernameAndPassword = decoder.decode("dXNlcm5hbWVwYXNzd29yZA==");
        assertEquals(usernameAndPassword, "usernamepassword");
    }
    
    @Test
    public void decodesAdminHunter2() {
        AuthorizationDecoder decoder = new AuthorizationDecoder();
        String usernameAndPassword = decoder.decode("YWRtaW46aHVudGVyMg==");
        assertEquals(usernameAndPassword, "admin:hunter2");
    }
}
