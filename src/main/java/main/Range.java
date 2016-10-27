package main;

import java.util.Arrays;

public class Range {

    private final String range;
    private final int startingValue;
    private final int endValue;

    public Range(String range) {
        this.range = range;
        this.startingValue = setStartingValue();
        this.endValue = setEndValue();
    }

    public int getStartingValue() {
        return startingValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public byte[] get(byte[] body) {
        if (range.startsWith("-")) return bytesFromEnd(body, endValue);
        if (range.endsWith("-")) return bytesFromBeginning(body, startingValue);
        return Arrays.copyOfRange(body, startingValue, endValue + 1);
    }

    private byte[] bytesFromBeginning(byte[] body, int startingPoint) {
        return Arrays.copyOfRange(body, startingPoint, body.length);
    }

    private byte[] bytesFromEnd(byte[] body, int endPoint) {
        return Arrays.copyOfRange(body, body.length - endPoint, body.length);
    }

    private int setStartingValue() {
        String[] numbers = range.split("-");
        if (range.endsWith("-") || range.startsWith("-")) {
            return Integer.parseInt(range.replaceAll("-", ""));
        }
        return Integer.parseInt(numbers[0]);
    }

    private  int setEndValue() {
        String[] numbers = range.split("-");
        if (range.endsWith("-")) {
            return Integer.parseInt(range.substring(0, range.length() - 1));
        } else {
            return Integer.parseInt(numbers[1]);
        }
    }
}
