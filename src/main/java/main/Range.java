package main;

import java.util.Arrays;

public class Range {

    private final String range;

    public Range(String range) {
        this.range = range;
    }

    public int getStartingValue() {
        String[] numbers = range.split("-");
        if (numbers[0].contains("=")) {
            return Integer.parseInt(numbers[0].split("")[1]);
        }
        return Integer.parseInt(numbers[0]);
    }

    public int getEndValue() {
        String[] numbers = range.split("-");
        return Integer.parseInt(numbers[1]);
    }
    public byte[] getRange(byte[] body, String bytes) {
        if (bytes.startsWith("=-")) return bytesFromEnd(body, getEndValue());
        if (bytes.endsWith("-")) return bytesFromBeginning(body, getStartingValue());
        return Arrays.copyOfRange(body, getStartingValue(), getEndValue()+ 1);
    }

    private byte[] bytesFromBeginning(byte[] body, int startingPoint) {
        return Arrays.copyOfRange(body, startingPoint, body.length);
    }

    private byte[] bytesFromEnd(byte[] body, int endPoint) {
        return Arrays.copyOfRange(body, body.length - endPoint, body.length);
    }
}
