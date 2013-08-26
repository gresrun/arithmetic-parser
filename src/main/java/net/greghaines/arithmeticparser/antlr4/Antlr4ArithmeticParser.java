package net.greghaines.arithmeticparser.antlr4;

import java.util.Stack;

import net.greghaines.arithmeticparser.ArithmeticParser;
import net.greghaines.arithmeticparser.antlr4.AntlrGenArithmeticParserParser.ExprContext;
import net.greghaines.arithmeticparser.antlr4.AntlrGenArithmeticParserParser.ExpressionContext;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * An implementation that uses Antlr 4 to lex and parse the expression.
 * 
 * @author Greg Haines
 */
public class Antlr4ArithmeticParser implements ArithmeticParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
        final AntlrGenArithmeticParserParser parser = new AntlrGenArithmeticParserParser(
                new CommonTokenStream(
                        new AntlrGenArithmeticParserLexer(
                                new ANTLRInputStream(expression))));
        final Listener listener = new Listener();
        new ParseTreeWalker().walk(listener, parser.expression());
        return listener.getResult();
    }
    
    private static final class Listener extends AntlrGenArithmeticParserBaseListener {

        private final Stack<Double> argStack = new Stack<Double>();
        private final Stack<Integer> opStack = new Stack<Integer>();

        public double getResult() {
            return this.argStack.pop();
        }

        /**
         * {@inheritDoc}
         */
        public void exitExpression(final ExpressionContext ctx) {
            handleExpression(ctx);
        }

        /**
         * {@inheritDoc}
         */
        public void exitExpr(final ExprContext ctx) {
            handleExpression(ctx);
        }

        private void handleExpression(final ParserRuleContext ctx) {
            if (ctx.getChildCount() == 3) { // Operations have 3 children
                final double right = this.argStack.pop();
                final double left = this.argStack.pop();
                final int op = this.opStack.pop();
                switch (op) {
                case AntlrGenArithmeticParserParser.MULTIPLICATION:
                    this.argStack.push(left * right);
                    break;
                case AntlrGenArithmeticParserParser.DIVISION:
                    this.argStack.push(left / right);
                    break;
                case AntlrGenArithmeticParserParser.ADDITION:
                    this.argStack.push(left + right);
                    break;
                case AntlrGenArithmeticParserParser.SUBTRACTION:
                    this.argStack.push(left - right);
                    break;
                }
            }
        }

        /**
         * {@inheritDoc}
         */
        public void visitTerminal(final TerminalNode node) {
            final Token symbol = node.getSymbol();
            final int type = symbol.getType();
            switch (type) {
            case AntlrGenArithmeticParserParser.NUMBER:
                this.argStack.push(Double.valueOf(symbol.getText()));
                break;
            case AntlrGenArithmeticParserParser.MULTIPLICATION:
            case AntlrGenArithmeticParserParser.DIVISION:
            case AntlrGenArithmeticParserParser.ADDITION:
            case AntlrGenArithmeticParserParser.SUBTRACTION:
                this.opStack.add(type);
                break;
            default:
                break;
            }
        }
    }
}
