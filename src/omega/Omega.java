/*
 * Copyright 2014 O. A. Riveros. All rights reserved.
 *
 * The use of any part of this software, it always allowed and where nonprofit,
 * whether to use the software for profit contact me to oscar.riveros@gmail.com
 * and let's talk about a commercial license.
 *
 */
package omega;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 *
 * @author O. A. Riveros
 */
public class Omega {

    static OmegaParser parser = new OmegaParser();
    static OmegaRuntime runtime = new OmegaRuntime();  

    public static String eval(String expr) {
        return runtime.eval(parser.parse(expr)).toString();
    }

    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();

        System.out.println("#####################################################");    
        System.out.println("#                                                   #");
        System.out.println("#                  .d88888888b.                     #");
        System.out.println("#                 d88P\"    \"Y88b                    #");
        System.out.println("#                 888        888                    #");
        System.out.println("#                 Y88b      d88P                    #");
        System.out.println("#                  \"88bo  od88\"                     #");
        System.out.println("#                 d88888  88888b                    #");
        System.out.println("#                                                   #");
        System.out.println("# Copyright 2014 O. A. Riveros. All rights reserved.#");
        System.out.println("#             oscar.riveros@gmail.com               #");
        System.out.println("#                                                   #");
        System.out.println("#####################################################");

        BufferedReader buffer;
        String expr = null;
        int line = 0;

        if (args.length == 1) {
            buffer = new BufferedReader(new FileReader(args[0]));
            while ((expr = buffer.readLine()) != null) {
                if (!expr.startsWith("(")) {                    
                    if (expr.startsWith(";")) {
                        System.out.println(String.format("%-4s \u03A9> %s", line, expr.replace(";", "")));
                    } else {
                        System.out.println(String.format("%-4s \u03A9> %s", line, expr));
                    }
                } else {
                    System.out.println(String.format("%-4s \u03A9> %s", line, Omega.eval(expr)));
                }

                line++;
            }

            buffer.close();
        } else {
            buffer = new BufferedReader(new InputStreamReader(System.in));

            while (!"(stop)".equals(expr)) {
                System.out.print(String.format("%-4s \u03A9> ", line++));
                expr = buffer.readLine();
                System.out.println(String.format("%-4s \u03A9> %s", line++, Omega.eval(expr)));                
            }

            buffer.close();
        }

        System.out.println(String.format("%-4s \u03A9> Elapsed time is %s milliseconds", line++, System.currentTimeMillis() - start));
    }
}
