package main;

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
}
