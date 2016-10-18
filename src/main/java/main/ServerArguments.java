package main;

import java.io.PrintStream;
import java.util.Arrays;

public class ServerArguments {

    private final String[] args;
    private final String defaultDirectory;
    private final PrintStream output;

    public ServerArguments(String[] args, PrintStream output) {
        this.args = args;
        this.output = output;
        this.defaultDirectory = "/Users/priyapatil/cob_spec/public";
    }

    public int getPort() {
        if (portGiven() && validPortFlag()) {
            showPortMessage();
            return Integer.valueOf(args[1]);
        }
        invalidPortMessage();
        return getDefaultPort();
    }

    public String getDirectory() {
        if ((args.length > 2) && args[2].startsWith("-d")) {
            showMessageSecondArg();
            return args[3];
        } else if ((args.length > 0) && validDirectoryFlag()) {
            showMessageFirstArg();
            return args[1];
        }
        invalidDirectoryMessage();
        return getDefaultDirectory();
    }

    private boolean portGiven() {
        return (args.length > 0) && validPortFlag();
    }

    public boolean validArguments() {
        return Arrays.asList(args).contains("-p") && Arrays.asList(args).contains("-d");
    }

    private boolean validPortFlag() {
        return args[0].equals("-p");
    }

    private boolean validDirectoryFlag() {
        return args[0].equals("-d");
    }

    private String getDefaultDirectory() {
        return defaultDirectory;
    }

    private int getDefaultPort() {
        return 5000;
    }

    private void showPortMessage() {
        output.println("Port : " + args[1]);
    }

    private void showMessageSecondArg() {
        output.println("Directory : " + args[3]);
    }

    private void showMessageFirstArg() {
        output.println("Directory : " + args[1]);
    }

    private void invalidPortMessage() {
        output.println("Invalid or Blank Port,\n" +
                       "Starting at Port : " + getDefaultPort() + "\n");
    }

    private void invalidDirectoryMessage() {
        output.println("Invalid or Blank Directory,\n" +
                       "Using Directory : " + getDefaultDirectory() + "\n");
    }
}
