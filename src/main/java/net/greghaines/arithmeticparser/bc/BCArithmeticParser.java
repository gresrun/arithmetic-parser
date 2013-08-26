package net.greghaines.arithmeticparser.bc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import net.greghaines.arithmeticparser.ArithmeticParser;

public class BCArithmeticParser implements ArithmeticParser {

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(final String expression) {
        if (System.getProperty("os.name").startsWith("Windows")) {
            throw new IllegalStateException("bc is a *nix utility");
        }
        final double result;
        try {
            final Process bcProc = new ProcessBuilder("bc").redirectErrorStream(true).start();
            final OutputStream os = bcProc.getOutputStream();
            os.write(("scale=10; " + expression + "\n").getBytes());
            os.flush();
//            bcProc.waitFor();
            final BufferedReader br = new BufferedReader(new InputStreamReader(bcProc.getInputStream()));
            final String resultStr = br.readLine();
            if (resultStr.contains("Divide by zero")) {
                result = Double.NaN;
            } else {
                result = Double.parseDouble(resultStr);
            }
            bcProc.destroy();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
