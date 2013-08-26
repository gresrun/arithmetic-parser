package net.greghaines.arithmeticparser.bc;

import net.greghaines.arithmeticparser.TestArithmeticParser;

import org.junit.Test;

public class TestBCArithmeticParser extends TestArithmeticParser {

    @Test
    public void testEvaluate() {
        doTest(new BCArithmeticParser());
    }
}
