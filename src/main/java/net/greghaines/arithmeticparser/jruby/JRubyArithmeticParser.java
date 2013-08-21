package net.greghaines.arithmeticparser.jruby;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.greghaines.arithmeticparser.ArithmeticParser;

import org.jruby.Ruby;
import org.jruby.RubyFloat;
import org.jruby.exceptions.RaiseException;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * An implementation that calls out to JRuby to evaluate the expression.
 * 
 * @author Greg Haines
 */
public class JRubyArithmeticParser implements ArithmeticParser {
    
    private static final Ruby runtime = Ruby.newInstance();
    private static final Pattern numberPattern = Pattern.compile("\\b(?<!\\.)(?:[1-9]\\d*|0)(?!\\.)\\b");
    private static final String toFloat = ".to_f";

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
        double result;
        try {
            final IRubyObject resultRubyObj = runtime.evalScriptlet(toFloatExpression(expression));
            final RubyFloat resultRubyFloat = resultRubyObj.convertToFloat();
            result = resultRubyFloat.getDoubleValue();
        } catch (RaiseException re) {
            if (re.getMessage().contains("ZeroDivisionError")) {
                // Handle division by zero
                result = Double.NaN;
            } else {
                // Wrap the Ruby exception as it does not contain the Java stack
                throw new RuntimeException(re);
            }
        }
        return result;
    }

    /**
     * Coerce all integers to doubles so there is never any integer arithmetic.
     * 
     * @param expression
     *            the original expression
     * @return the expression with all integers coerced to doubles
     */
    private static String toFloatExpression(final String expression) {
        final StringBuilder exprSB = new StringBuilder(expression);
        final Matcher matcher = numberPattern.matcher(exprSB);
        int index = 0;
        while (matcher.find(index)) {
            exprSB.insert(matcher.end(), toFloat);
            index = matcher.start() + toFloat.length() + matcher.group().length();
        }
        return exprSB.toString();
    }
}
