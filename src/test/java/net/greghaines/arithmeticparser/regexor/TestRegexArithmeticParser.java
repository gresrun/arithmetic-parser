package net.greghaines.arithmeticparser.regexor;

import net.greghaines.arithmeticparser.TestArithmeticParser;
import org.junit.Test;

public class TestRegexArithmeticParser extends TestArithmeticParser {

    @Test
    public void testEvaluate() {
        doTest(new RegexArithmeticParser());
    }
}
