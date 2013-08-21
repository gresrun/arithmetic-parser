package net.greghaines.arithmeticparser;

import static org.junit.Assert.assertEquals;

/**
 * Extend this class for each implementation. Each subclass only needs to make a
 * single method:
 * 
 * <pre>
 * {@literal @}Test
 * public void testEvaluate() {
 *     doTest(new YourArithmeticParser());
 * }
 * </pre>
 * 
 * @author Greg Haines
 */
public abstract class TestArithmeticParser {

    private static final double DELTA = 0.00001;

    /**
     * Send an ArithmeticParser implementation to see if it passes.
     * 
     * @param parser
     *            the implementation to test
     */
    protected void doTest(final ArithmeticParser parser) {
        // Constants
        assertEquals(0.0, parser.evaluate("0"), DELTA);
        assertEquals(0.0, parser.evaluate("0.0"), DELTA);
        assertEquals(1.0, parser.evaluate("1"), DELTA);
        assertEquals(1.0, parser.evaluate("1.0"), DELTA);
        assertEquals(1.23, parser.evaluate("1.23"), DELTA);
        assertEquals(54321.0, parser.evaluate("54321"), DELTA);
        assertEquals(54321.321, parser.evaluate("54321.321"), DELTA);
        // Parenthetical Constants
        assertEquals(0.0, parser.evaluate("(0)"), DELTA);
        assertEquals(0.0, parser.evaluate("(0.0)"), DELTA);
        assertEquals(1.0, parser.evaluate("(1)"), DELTA);
        assertEquals(1.0, parser.evaluate("(1.0)"), DELTA);
        assertEquals(1.23, parser.evaluate("(1.23)"), DELTA);
        assertEquals(54321.0, parser.evaluate("(54321)"), DELTA);
        assertEquals(54321.321, parser.evaluate("(54321.321)"), DELTA);
        // Basic Addition
        assertEquals(0.0, parser.evaluate("0 + 0"), DELTA);
        assertEquals(2.0, parser.evaluate("1 + 1"), DELTA);
        assertEquals(246.0, parser.evaluate("123 + 123"), DELTA);
        assertEquals(369.0, parser.evaluate("123 + 123 + 123"), DELTA);
        assertEquals(2.5, parser.evaluate("1 + 1.5"), DELTA);
        assertEquals(3.0, parser.evaluate("1.5 + 1.5"), DELTA);
        assertEquals(247.0, parser.evaluate("123.5 + 123.5"), DELTA);
        assertEquals(370.5, parser.evaluate("123.5 + 123.5 + 123.5"), DELTA);
        // Basic Subtraction
        assertEquals(0.0, parser.evaluate("0 - 0"), DELTA);
        assertEquals(0.0, parser.evaluate("1 - 1"), DELTA);
        assertEquals(0.0, parser.evaluate("123 - 123"), DELTA);
        assertEquals(-123.0, parser.evaluate("123 - 123 - 123"), DELTA);
        assertEquals(1.5, parser.evaluate("2.5 - 1.0"), DELTA);
        assertEquals(0.0, parser.evaluate("1.5 - 1.5"), DELTA);
        assertEquals(0.0, parser.evaluate("123.5 - 123.5"), DELTA);
        assertEquals(-123.5, parser.evaluate("123.5 - 123.5 - 123.5"), DELTA);
        // Basic Multiplication
        assertEquals(0.0, parser.evaluate("0 * 0"), DELTA);
        assertEquals(1.0, parser.evaluate("1 * 1"), DELTA);
        assertEquals(15129.0, parser.evaluate("123 * 123"), DELTA);
        assertEquals(1860867.0, parser.evaluate("123 * 123 * 123"), DELTA);
        assertEquals(1.5, parser.evaluate("1 * 1.5"), DELTA);
        assertEquals(2.25, parser.evaluate("1.5 * 1.5"), DELTA);
        assertEquals(15252.25, parser.evaluate("123.5 * 123.5"), DELTA);
        assertEquals(1883652.875, parser.evaluate("123.5 * 123.5 * 123.5"), DELTA);
        // Basic Division
        assertEquals(Double.NaN, parser.evaluate("0 / 0"), DELTA);
        assertEquals(1.0, parser.evaluate("1 / 1"), DELTA);
        assertEquals(1.5, parser.evaluate("3 / 2"), DELTA);
        assertEquals(0.00813008130081, parser.evaluate("123 / 123 / 123"), DELTA);
        assertEquals(1.5, parser.evaluate("1.5 / 1"), DELTA);
        assertEquals(1.33333333333333, parser.evaluate("2 / 1.5"), DELTA);
        assertEquals(1.0, parser.evaluate("123.5 / 123.5"), DELTA);
        assertEquals(0.0080971659919, parser.evaluate("123.5 / 123.5 / 123.5"), DELTA);
        // Basic Arithmetic
        assertEquals(2.0, parser.evaluate("1 + 2 - 1"), DELTA);
        assertEquals(0.0, parser.evaluate("3 - 4 + 1"), DELTA);
        assertEquals(-0.5, parser.evaluate("1 + 3 / -2"), DELTA);
        assertEquals(123.0, parser.evaluate("123 * 123 / 123"), DELTA);
        assertEquals(-1.6, parser.evaluate("1.5 / 1 - 0.5 * 6.2"), DELTA);
        assertEquals(1.0, parser.evaluate("2 * 1.5 - 2"), DELTA);
        assertEquals(Double.NEGATIVE_INFINITY, parser.evaluate("123.5 - 123.5 / 0"), DELTA);
        assertEquals(20.5, parser.evaluate("3 * 6 + 2.5"), DELTA);
        // Parenthetical Arithmetic
        assertEquals(2.0, parser.evaluate("1 + (2 - 1)"), DELTA);
        assertEquals(0.0, parser.evaluate("((3 - 4) + 1)"), DELTA);
        assertEquals(2.0, parser.evaluate("(1 + 3) / 2"), DELTA);
        assertEquals(123.0, parser.evaluate("123 * 123 / 123"), DELTA);
        assertEquals(-1.6, parser.evaluate("1.5 / 1 - 0.5 * 6.2"), DELTA);
        assertEquals(1.0, parser.evaluate("2 * 1.5 - 2"), DELTA);
        assertEquals(Double.NEGATIVE_INFINITY, parser.evaluate("123.5 - 123.5 / 0"), DELTA);
        assertEquals(20.5, parser.evaluate("3 * 6 + 2.5"), DELTA);
        // Advanced Stuff
        assertEquals(17.0, parser.evaluate("12 + (32 - (8/2) * (3)) / 4"), DELTA);
        assertEquals(14.875, parser.evaluate("(12- 2+1/ (4  *2)) + (32 - (8/2) * (3)) / 4"), DELTA);
    }
}
