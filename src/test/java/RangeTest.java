import main.Range;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangeTest {
    @Test
    public void getsStartingValue() {
        Range range = new Range("66-101");
        assertEquals(66, range.getStartingValue());
    }

    @Test
    public void getsAnotherStartingValue() {
        Range range = new Range("555-101");
        assertEquals(555, range.getStartingValue());
    }

    @Test
    public void getsEndValue() {
        Range range = new Range("66-101");
        assertEquals(101, range.getEndValue());
    }

    @Test
    public void getsAnotherEndValue() {
        Range range = new Range("66-111");
        assertEquals(111, range.getEndValue());
    }

    @Test
    public void getsEndValueOfHalfRange() {
        Range range = new Range("-111");
        assertEquals(111, range.getEndValue());
    }

    @Test
    public void getsAnotherEndValueOfHalfRange() {
        Range range = new Range("-122");
        assertEquals(122, range.getEndValue());
    }

    @Test
    public void getsBeginningValueOfFirstHalfRange() {
        Range range = new Range("22-");
        assertEquals(22, range.getStartingValue());
    }

    @Test
    public void getsBeginningValueForMinusSix() {
        Range range = new Range("=-6");
        assertEquals(6, range.getEndValue());
    }

    @Test
    public void getsEndValueForMinusSix() {
        Range range = new Range("=6-");
        assertEquals(6, range.getStartingValue());
    }
}
