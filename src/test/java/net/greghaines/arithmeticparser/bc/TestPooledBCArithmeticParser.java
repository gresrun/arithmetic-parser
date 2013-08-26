package net.greghaines.arithmeticparser.bc;

import net.greghaines.arithmeticparser.TestArithmeticParser;

import org.junit.Test;

public class TestPooledBCArithmeticParser extends TestArithmeticParser {

    @Test
    public void testEvaluate() {
        doTest(new PooledBCArithmeticParser());
    }
}
