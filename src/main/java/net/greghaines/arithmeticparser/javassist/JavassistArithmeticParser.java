package net.greghaines.arithmeticparser.javassist;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Modifier;
import net.greghaines.arithmeticparser.ArithmeticParser;

/**
 * An implementation of ArithmeticParser that creates a dynamic class to evaluate the expression.
 * 
 * @author Greg Haines
 */
public class JavassistArithmeticParser implements ArithmeticParser {
	
	private static final ClassPool classPool = new ClassPool(true);
	private static final AtomicLong counter = new AtomicLong(0);
	private static final Pattern numberPattern = Pattern.compile("\\b(?<!\\.)(?:[1-9]\\d*|0)(?!\\.)\\b");
	private static final String doubleCast = "(double)";
	
	static {
        classPool.appendClassPath(new ClassClassPath(NoOpArithmeticParser.class));
    }
	
	private final Method defineClassMethod;

	/**
	 * Constructor.
	 */
	public JavassistArithmeticParser() {
		try {
			this.defineClassMethod = ClassLoader.class.getDeclaredMethod(
			        "defineClass", String.class, byte[].class, int.class, int.class);
	        this.defineClassMethod.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double evaluate(final String expression) {
		double result;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	        if (classLoader == null) {
	            classLoader = ClassLoader.getSystemClassLoader();
	        }
	        final String className = "net.greghaines.arithmeticparser.javassist.__parser__.Parser" + counter.getAndIncrement();
	        final CtClass c = classPool.getAndRename(NoOpArithmeticParser.class.getName(), className);
	        c.setModifiers(c.getModifiers() | Modifier.FINAL);
	        c.getDeclaredMethod("evaluate").setBody("{ return " + castedExpression(expression) + "; }");
	        final byte[] byteCode = c.toBytecode();
	        c.detach();
	        final Class<?> genClazz = (Class<?>) this.defineClassMethod.invoke(classLoader, className, byteCode, 0, byteCode.length);
	        final ArithmeticParser genObj = (ArithmeticParser) genClazz.newInstance();
	        result = genObj.evaluate(null); // Can send null because argument is ignored in generated implementations
		} catch (ArithmeticException ae) {
			// So... Javassist can blow up with a ArithmeticException when 
			// compiling an expression with integer division by zero because 
			// they don't check.
			result = Double.NaN;
		} catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		return result;
	}

	private static String castedExpression(final String expression) {
		final StringBuilder exprSB = new StringBuilder(expression);
		final Matcher matcher = numberPattern.matcher(exprSB);
		int index = 0;
		while (matcher.find(index)) {
			final int startIndex = matcher.start();
			exprSB.insert(startIndex, doubleCast);
			index = startIndex + doubleCast.length() + matcher.group().length();
		}
		return exprSB.toString();
	}
	
	/**
	 * An implementation of ArithmeticParser that always returns zero.
	 * Used as a basis for Javassist-created parsers.
	 * 
	 * @author Greg Haines
	 */
	public static class NoOpArithmeticParser implements ArithmeticParser {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double evaluate(final String expression) {
			return 0.0;
		}
	}
}
