package response;

import main.request.Request;
import main.Response;
import main.responses.ResourceResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static main.Status.OK;
import static main.Status.PARTIAL;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class ResourceResponseTest {

    private final String publicDirectory = "/Users/priyapatil/cob_spec/public";
    List content = new ArrayList<>();
    private final TestHelper helper = new TestHelper();
    private Request getImageGif;
    private Request getTextFile;
    private Request getImageJPEG;
    private Request getImagePNG;
    private ResourceResponse resourceResponse;
    private Request getPartial;
    private Request getPartialTwo;
    private Request getPartialThree;

    @Before
    public void setUp() {
        getImageGif = helper.create("GET /image.gif");
        getTextFile = helper.create("GET /text-file.txt");
        getImageJPEG = helper.create("GET /image.jpeg");
        getImagePNG = helper.create("GET /image.png");
        getPartial = helper.createPartial("GET /partial_content.txt", 7);
        getPartialTwo = helper.createPartial("GET /partial_content.txt", 9);
        getPartialThree = helper.createPartialEnd("GET /partial_content.txt", 3);
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

    @Test
    public void headerContainsGifMediaType() {
        Response response = resourceResponse.get(getImageGif);
        assertThat(response.getHeader(), containsString("Content-Type: image/gif\n"));
    }

    @Test
    public void headerContainsJpegMediaType() {
        Response response = resourceResponse.get(getImageJPEG);
        assertThat(response.getHeader(), containsString("Content-Type: image/jpeg\n"));
    }

    @Test
    public void headerContainsPlainTextMediaType() {
        Response response = resourceResponse.get(getTextFile);
        assertThat(response.getHeader(), containsString("Content-Type: text/plain\n"));
    }

    @Test
    public void partialRequestReturnsPartialStatus() {
        Response response = resourceResponse.get(getPartial);
        assertThat(response.getHeader(), containsString(PARTIAL.get()));
    }

    @Test
    public void partialRequestReturnsHeaderWithCorrectBytes() {
        Response response = resourceResponse.get(getPartial);
        assertTrue(response.getBody().length == 8);
    }

    @Test
    public void anotherPartialRequestReturnsHeaderWithCorrectBytes() {
        Response response = resourceResponse.get(getPartialTwo);
        assertTrue(response.getBody().length == 10);
    }
    
    @Test
    public void partialAskingForBytesFromEndOfFile() {
        Response response = resourceResponse.get(getPartialThree);
        assertTrue(response.getBody().length == 3);
    }
}
