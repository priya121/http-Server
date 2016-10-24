import main.ServerArguments;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServerArgumentsTest {
    private final ByteArrayOutputStream recordedOutput = new ByteArrayOutputStream();
    private final PrintStream output = new PrintStream(recordedOutput);

    @Test
    public void getsPortGivenAsAnArgument() {
        String[] args = {"-p", "4000", "-d", "/Users/richard/cob_spec/public"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals(4000, arguments.getPort());
    }

    @Test
    public void messageStartingAtDirectory() {
        String[] args = {"-p", "4000", "-d", "/Users/richard/cob_spec/public"};
        ServerArguments arguments = new ServerArguments(args, output);
        arguments.getDirectory();
        assertEquals("Directory : /Users/richard/cob_spec/public\n", recordedOutput.toString());
    }

    @Test
    public void getsDirectoryGivenArgument() {
        String[] args = {"-p", "4000", "-d", "/Users/richard/cob_spec/public"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals("/Users/richard/cob_spec/public", arguments.getDirectory());
    }

    @Test
    public void getsPortWhenNoDirectory() {
        String[] args = {"-p", "4000"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals(4000, arguments.getPort());
    }

    @Test
    public void messageUsingPort() {
        String[] args = {"-p", "4000", "-d", "/Users/richard/cob_spec/public"};
        ServerArguments arguments = new ServerArguments(args, output);
        arguments.getPort();
        assertEquals("Port : 4000\n", recordedOutput.toString());
    }

    @Test
    public void getsDirectoryWhenNoPortGiven() {
        String[] args = {"-d", "/Users/richard/cob_spec/public"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals("/Users/richard/cob_spec/public", arguments.getDirectory());
    }

    @Test
    public void returnsDefaultDirectoryGivenNoArguments() {
        String[] args = {};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals("/Users/priyapatil/cob_spec/public", arguments.getDirectory());
    }

    @Test
    public void returnsDefaultPortGivenNoArguments() {
        String[] args = {};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals(5000, arguments.getPort());
    }

    @Test
    public void returnsDefaultPortForIncorrectArguments() {
        String[] args = {"ljahsfda"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals(5000, arguments.getPort());
    }

    @Test
    public void returnsDefaultDirectoryForIncorrectArguments() {
        String[] args = {"ljahsfda"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals(5000, arguments.getPort());
    }

    @Test
    public void doesNotAllowIncorrectArguments() {
        String[] args = {"ljahsfda"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertEquals("/Users/priyapatil/cob_spec/public", arguments.getDirectory());
    }

    @Test
    public void determineIfIncorrectFlagsGiven() {
        String[] args = {"ljahsfda"};
        ServerArguments arguments = new ServerArguments(args, output);
        assertFalse(arguments.validArguments());
    }

    @Test
    public void messageWhenPortIncorrect() {
        String[] args = {"ljahsfda"};
        ServerArguments arguments = new ServerArguments(args, output);
        arguments.getPort();
        assertEquals("Invalid or Blank Port,\nStarting at Port : 5000\n\n", recordedOutput.toString());
    }

    @Test
    public void messageWhenDirectoryIncorrect() {
        String[] args = {"ljahsfda"};
        ServerArguments arguments = new ServerArguments(args, output);
        arguments.getDirectory();
        assertEquals("Invalid or Blank Directory,\nUsing Directory " +
                    ": /Users/priyapatil/cob_spec/public\n\n", recordedOutput.toString());
    }
}
