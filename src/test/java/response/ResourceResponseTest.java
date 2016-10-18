package response;

import main.Request;
import main.Response;
import main.responses.ResourceResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static main.Status.OK;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class ResourceResponseTest {

    String publicDirectory = "/Users/priyapatil/cob_spec/public";
    List content = new ArrayList<>();
    private final TestHelper helper = new TestHelper();
    private Request getImageGif;
    private Request getTextFile;
    private Request getImageJPEG;
    private Request getImagePNG;
    private ResourceResponse resourceResponse;

    @Before
    public void setUp() {
        getImageGif = helper.create("GET /image.gif");
        getTextFile = helper.create("GET /text-file.txt");
        getImageJPEG = helper.create("GET /image.jpeg");
        getImagePNG = helper.create("GET /image.png");
        resourceResponse = new ResourceResponse(publicDirectory, content);
    }

    @Test
    public void OKStatusForImageGifFile() {
        Response response = resourceResponse.get(getImageGif);
        assertThat(response.getHeader(), containsString(OK.get()));
    }

    @Test
    public void addsImageGIFContentsToBody() {
        Response response = resourceResponse.get(getImageGif);
        assertTrue(response.getBody().length != 0);
    }

    @Test
    public void OKStatusForTextFile() {
        Response response = resourceResponse.get(getTextFile);
        assertThat(response.getHeader(), containsString(OK.get()));
    }

    @Test
    public void addsTextFileContentsToBody() {
        Response response = resourceResponse.get(getTextFile);
        assertTrue(response.getBody().length != 0);
    }

    @Test
    public void OKStatusForJPEGFile() {
        Response response = resourceResponse.get(getImageJPEG);
        assertThat(response.getHeader(), containsString(OK.get()));
    }

    @Test
    public void addsImagePNGContentToBody() {
        Response response = resourceResponse.get(getImagePNG);
        assertTrue(response.getBody().length != 0);
    }

}
