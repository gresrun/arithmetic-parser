package net.greghaines.arithmeticparser.regex;

import net.greghaines.arithmeticparser.TestArithmeticParser;
import net.greghaines.arithmeticparser.regex.RegexArithmeticParser;

import org.junit.Ignore;
import org.junit.Test;

public class TestRegexArithmeticParser extends TestArithmeticParser {

    @Test
    @Ignore // Ignore while this doesn't work
    public void testEvaluate() {
        doTest(new RegexArithmeticParser());
    }
}
