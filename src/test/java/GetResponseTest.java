import main.GetResponse;
import main.request.Request;
import org.junit.Before;
import org.junit.Test;
import response.TestHelper;

public class GetResponseTest {
    private Request getCoffeeRequest;
    private final TestHelper helper = new TestHelper();

    @Before
    public void setUp() {
        getCoffeeRequest = helper.create("GET /coffee");
    }

    @Test
    public void getsExistingResource() {
        GetResponse response = new GetResponse();
    }
}
