package net.greghaines.arithmeticparser.javassist;

import net.greghaines.arithmeticparser.TestArithmeticParser;

import org.junit.Test;

public class TestJavassistArithmeticParser extends TestArithmeticParser {

    @Test
    public void testEvaluate() {
        doTest(new JavassistArithmeticParser());
    }
}
