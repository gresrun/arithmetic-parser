package net.greghaines.arithmeticparser.js;

import net.greghaines.arithmeticparser.TestArithmeticParser;
import net.greghaines.arithmeticparser.js.JavaScriptArithmeticParser;

import org.junit.Test;

public class TestJavaScriptArithmeticParser extends TestArithmeticParser {

    @Test
    public void testExecute() {
        doTest(new JavaScriptArithmeticParser());
    }
}
