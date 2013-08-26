package net.greghaines.arithmeticparser.antlr4;

import net.greghaines.arithmeticparser.TestArithmeticParser;

import org.junit.Test;

public class TestAntlr4ArithmeticParser extends TestArithmeticParser {

    @Test
    public void testEvaluate() {
        doTest(new Antlr4ArithmeticParser());
    }
}
