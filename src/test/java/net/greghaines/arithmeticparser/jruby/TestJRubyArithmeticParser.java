package net.greghaines.arithmeticparser.jruby;

import net.greghaines.arithmeticparser.TestArithmeticParser;

import org.junit.Test;

public class TestJRubyArithmeticParser extends TestArithmeticParser {

    @Test
    public void testEvaluate() {
        doTest(new JRubyArithmeticParser());
    }
}
