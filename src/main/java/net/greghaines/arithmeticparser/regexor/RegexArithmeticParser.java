package net.greghaines.arithmeticparser.regexor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.greghaines.arithmeticparser.ArithmeticParser;

public class RegexArithmeticParser implements ArithmeticParser {

  @Override
  public double evaluate(String expression) {
    String text = expression;
    Matcher p = Pattern.compile("\\(([^\\)\\(]+)\\)").matcher(text);
    while(p.reset(text).find())
      text = text.substring(0, p.start()) + evaluate(p.group(1)) + text.substring(p.end());
    for(String op : new String[]{"[\\*\\/]", "[\\+\\-]"}) {
      p.usePattern(Pattern.compile("([0-9]+(\\.[0-9]+)?)\\s*("+op+")\\s*([0-9]+(\\.[0-9]+)?)"));
      while(p.reset(text).find())
        text = text.substring(0, p.start())
            + (p.group(3).equals("*")?(evaluate(p.group(1)) * evaluate(p.group(4))):
              (p.group(3).equals("/")?(evaluate(p.group(4))==0?1/0:(evaluate(p.group(1)) / evaluate(p.group(4)))):
                (p.group(3).equals("+")?(evaluate(p.group(1)) + evaluate(p.group(4))):
                  (evaluate(p.group(1)) - evaluate(p.group(4))))))
          + text.substring(p.end());
    }
    return Double.parseDouble(text);
  }

}
