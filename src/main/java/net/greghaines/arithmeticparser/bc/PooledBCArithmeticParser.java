package net.greghaines.arithmeticparser.bc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import net.greghaines.arithmeticparser.ArithmeticParser;

/**
 * An implementation that passes on the work of calculation to the
 * UNIX utility 'bc' and shares a single process per instance.
 * 
 * @author Greg Haines
 */
public class PooledBCArithmeticParser implements ArithmeticParser {
    
    private final Process bcProc;
    private final OutputStream os;
    private final BufferedReader br;
    
    public PooledBCArithmeticParser() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            throw new IllegalStateException("bc is a *nix utility");
        }
        try {
            this.bcProc = new ProcessBuilder("bc").redirectErrorStream(true).start();
            this.os = this.bcProc.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(this.bcProc.getInputStream()));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
        final double result;
        try {
            this.os.write(("scale=10; " + expression + "\n").getBytes());
            this.os.flush();
            final String resultStr = this.br.readLine();
            if (resultStr.contains("Divide by zero")) {
                result = Double.NaN;
            } else {
                result = Double.parseDouble(resultStr);
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void finalize() throws Throwable {
        this.bcProc.destroy();
        super.finalize();
    }
}
