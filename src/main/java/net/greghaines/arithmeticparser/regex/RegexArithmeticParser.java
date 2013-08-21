package net.greghaines.arithmeticparser.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.greghaines.arithmeticparser.ArithmeticParser;

public class RegexArithmeticParser implements ArithmeticParser {
    
    private static final Pattern parenPattern = Pattern.compile("\\(([^\\)\\(]+)\\)");
    private static final String numericPatternStr = "([-]?(?:[0-9]+(?:\\.[0-9]+)?|Infinity|NaN))";
    private static final Pattern multiDividePattern = Pattern.compile(numericPatternStr + "\\s*([\\*\\/])\\s*" + numericPatternStr);
    private static final Pattern addSubPattern = Pattern.compile(numericPatternStr + "\\s*([\\+\\-])\\s*" + numericPatternStr);
    private static final Pattern[] opPatterns = { multiDividePattern, addSubPattern };

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
        final StringBuilder text = new StringBuilder(expression);
        final Matcher p = parenPattern.matcher(text);
        while (p.reset(text).find()) {
            text.replace(p.start(), p.end(), Double.toString(evaluate(p.group(1))));
        }
        for (final Pattern opPattern : opPatterns) {
            p.usePattern(opPattern);
            while (p.reset(text).find()) {
                final String arg1 = p.group(1);
                final String operator = p.group(2);
                final String arg2 = p.group(3);
                final double result;
                switch (operator) {
                case "*":
                    result = evaluate(arg1) * evaluate(arg2);
                    break;
                case "/":
                    result = evaluate(arg1) / evaluate(arg2);
                    break;
                case "+":
                    result = evaluate(arg1) + evaluate(arg2);
                    break;
                case "-":
                    result = evaluate(arg1) - evaluate(arg2);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown operator: " + operator);
                }
                text.replace(p.start(), p.end(), Double.toString(result));
            }
        }
        return Double.parseDouble(text.toString());
    }
}
