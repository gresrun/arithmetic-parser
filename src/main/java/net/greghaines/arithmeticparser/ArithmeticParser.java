package net.greghaines.arithmeticparser;

/**
 * A basic interface for arithmetic parsers.
 * 
 * @author Greg Haines
 */
public interface ArithmeticParser {

    /**
     * Evaluate an arithmetic expression.
     * 
     * @param expression
     *            the arithmetic expression
     * @return the number that the expression evaluated to
     */
    double evaluate(String expression);
}
