import main.FilePathList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilePathTest {
    FilePathList list = new FilePathList();

    @Test
    public void formPathIsValid() {
        assertTrue(list.validFilePath("/text-file.txt"));
    }

    @Test
    public void redirectPathIsValid() {
        assertTrue(list.validFilePath("/redirect"));
    }

    @Test
    public void coffeePathIsValid() {
        assertTrue(list.validFilePath("/coffee"));
    }

    @Test
    public void textFilePathIsValid() {
        assertTrue(list.validFilePath("/text-file.txt"));
    }

    @Test
    public void invalidFilePath() {
        assertFalse(list.validFilePath("/foobar"));
    }

    @Test
    public void storesAllValidFilePaths() {
        assertEquals(7, list.getFiles().size());
    }
}
