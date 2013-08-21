package net.greghaines.arithmeticparser.regexor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.greghaines.arithmeticparser.ArithmeticParser;

public class RegexArithmeticParser implements ArithmeticParser {
    
    private static final Pattern parenPattern = Pattern.compile("\\(([^\\)\\(]+)\\)");
    private static final Pattern multiDividePattern = Pattern.compile("([-]?[0-9]+(?:\\.[0-9]+)?)\\s*([\\*\\/])\\s*([-]?[0-9]+(?:\\.[0-9]+)?)");
    private static final Pattern addSubPattern = Pattern.compile("([-]?[0-9]+(?:\\.[0-9]+)?)\\s*([\\+\\-])\\s*([-]?[0-9]+(?:\\.[0-9]+)?)");
    private static final Pattern[] opPatterns = { multiDividePattern, addSubPattern };

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
//        System.out.println("evaluate: " + expression);
        String text = expression;
        final Matcher p = parenPattern.matcher(text);
        while (p.reset(text).find()) {
            text = text.substring(0, p.start()) + evaluate(p.group(1)) + text.substring(p.end());
        }
        for (final Pattern opPattern : opPatterns) {
            p.usePattern(opPattern);
            while (p.reset(text).find()) {
//                System.out.println("PRE text: " + text);
                final String arg1 = p.group(1);
                final String operator = p.group(2);
                final String arg2 = p.group(3);
//                System.out.println("arg1: " + arg1);
//                System.out.println("operator: " + operator);
//                System.out.println("arg2: " + arg2);
                final double result;
                switch (operator) {
                case "*": {
                    result = evaluate(arg1) * evaluate(arg2);
                    break;
                }
                case "/": {
                    result = evaluate(arg1) / evaluate(arg2);
                    break;
                }
                case "+": {
                    result = evaluate(arg1) + evaluate(arg2);
                    break;
                }
                case "-": {
                    result = evaluate(arg1) - evaluate(arg2);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown operator: " + operator);
                }
                }
//                System.out.println("result: " + result);
                text = text.substring(0, p.start()) + result + text.substring(p.end());
//                System.out.println("POST text: " + text);
            }
        }
        return Double.parseDouble(text);
    }
}
