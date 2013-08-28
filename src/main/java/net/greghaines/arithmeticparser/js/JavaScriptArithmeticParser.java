package net.greghaines.arithmeticparser.js;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.greghaines.arithmeticparser.ArithmeticParser;

/**
 * An implementation that uses Java's built-in JavaScript parser to evaluate the expression.
 * 
 * @author Greg Haines
 */
public class JavaScriptArithmeticParser implements ArithmeticParser {
    
    private final ScriptEngine engine;
    
    public JavaScriptArithmeticParser() {
        this("JavaScript");
    }
    
    public JavaScriptArithmeticParser(final String engineName) {
        this.engine = new ScriptEngineManager().getEngineByName(engineName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
        final double result;
        try {
            final Object resultObj = this.engine.eval(expression);
            if (resultObj instanceof Number) {
                result = ((Number) resultObj).doubleValue();
            } else if (resultObj instanceof String) {
                result = Double.parseDouble((String) resultObj);
            } else {
                throw new IllegalStateException("Unknown return type: " + resultObj);
            }
        } catch (ScriptException se) {
            throw new RuntimeException(se);
        }
        return result;
    }
}
