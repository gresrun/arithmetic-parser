package net.greghaines.arithmeticparser.regexor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.greghaines.arithmeticparser.ArithmeticParser;

public class RegexArithmeticParser implements ArithmeticParser {
    
    private static final Pattern parenPattern = Pattern.compile("\\(([^\\)\\(]+)\\)");
    private static final Pattern multiDividePattern = Pattern.compile("([0-9]+(\\.[0-9]+)?)\\s*([\\*\\/])\\s*([0-9]+(\\.[0-9]+)?)");
    private static final Pattern addSubPattern = Pattern.compile("([0-9]+(\\.[0-9]+)?)\\s*([\\+\\-])\\s*([0-9]+(\\.[0-9]+)?)");
    private static final Pattern[] opPatterns = { multiDividePattern, addSubPattern };

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
        String text = expression;
        final Matcher p = parenPattern.matcher(text);
        while (p.reset(text).find()) {
            text = text.substring(0, p.start()) + evaluate(p.group(1)) + text.substring(p.end());
        }
        for (final Pattern opPattern : opPatterns) {
            p.usePattern(opPattern);
            while (p.reset(text).find()) {
                text = text.substring(0, p.start())
                        + (p.group(3).equals("*") ? (evaluate(p.group(1)) * evaluate(p.group(4))) : (p.group(3).equals("/") ? (evaluate(p.group(4)) == 0 ? Double.NaN
                                : (evaluate(p.group(1)) / evaluate(p.group(4)))) : (p.group(3).equals("+") ? (evaluate(p.group(1)) + evaluate(p.group(4))) : (evaluate(p.group(1)) - evaluate(p
                                .group(4)))))) + text.substring(p.end());
            }
        }
        return Double.parseDouble(text);
    }
}
