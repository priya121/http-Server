import main.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class LoggerTest {
    private File file;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        file = folder.newFile("logs");
    }

    @Test
    public void addsToLog() throws IOException {
        Logger logger = new Logger(new File(file.getPath()));
        logger.log("Hi how are you?");
        assertEquals(readContents(),"Hi how are you?\n");
    }

    private String readContents() throws IOException {
        String contents = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contents += currentLine + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
}
