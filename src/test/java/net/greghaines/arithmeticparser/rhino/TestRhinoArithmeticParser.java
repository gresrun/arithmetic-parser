package net.greghaines.arithmeticparser.rhino;

import net.greghaines.arithmeticparser.TestArithmeticParser;

import org.junit.Test;

public class TestRhinoArithmeticParser extends TestArithmeticParser {

    @Test
    public void testExecute() {
        doTest(new RhinoArithmeticParser());
    }
}
