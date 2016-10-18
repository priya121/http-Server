import main.Request;
import org.junit.Before;
import org.junit.Test;
import response.TestHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ResourceResponseTest {
    String publicDirectory = "/Users/priyapatil/cob_spec/public";
    private final TestHelper helper = new TestHelper();
    private Request getImageGif;
    private Request getTextFile;
    private Request getFoobar;
    private Request getImageJPEG;
    private Request getImagePNG;

    @Before
    public void setUp() {
        getImageGif = helper.create("GET /image.gif");
        getTextFile = helper.create("GET /text-file.txt");
        getImageJPEG = helper.create("GET /image.jpeg");
        getImagePNG = helper.create("GET /image.png");
        getFoobar = helper.create("GET /foobar");
    }

    @Test
    public void hasListOfAllPossibleResources() {
        ResourceNotUsed getResource = new ResourceNotUsed(publicDirectory, getImageGif);
        assertThat(getResource.getFiles().size(), is(8));
    }

    @Test
    public void canFindExisitingImageFile() {
        ResourceNotUsed getResource = new ResourceNotUsed(publicDirectory, getImageGif);
        assertTrue(getResource.requestPossible(getImageGif));
    }

    @Test
    public void canFindExisitingTextFile() {
        ResourceNotUsed getResource = new ResourceNotUsed(publicDirectory, getTextFile);
        assertTrue(getResource.requestPossible(getTextFile));
    }

    @Test
    public void noFileForNonExistentFile() {
        ResourceNotUsed getResource = new ResourceNotUsed(publicDirectory, getFoobar);
        assertFalse(getResource.requestPossible(getFoobar));
    }

    @Test
    public void retrunsOK200ForExistingFile() {
        ResourceNotUsed getResource = new ResourceNotUsed(publicDirectory, getTextFile);
        //assertEquals("HTTP/1.1 200 OK\n\n", getResource.getResponse().getHeader());
    }

    @Test
    public void addsContentBytesToResponseBody() {
        ResourceNotUsed getResource = new ResourceNotUsed(publicDirectory, getTextFile);
        assertFalse(getResource.requestBody().length == 0);
    }

}
