/*
 * Copyright 2014 O. A. Riveros. All rights reserved.
 *
 * The use of any part of this software, it always allowed and where nonprofit,
 * whether to use the software for profit contact me to oscar.riveros@gmail.com
 * and let'expr talk about a commercial license.
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
    static OmegaInterpreter interpreter = new OmegaInterpreter();
    private static String expression;

    public static String eval(String expr) {
        return interpreter.eval(parser.parse(expr)).toString();
    }

    public static void main(String args[]) throws Exception {
        System.out.println("#####################################################");
        System.out.println("#                      Omega                        #");
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
        System.out.println("#####################################################");

        BufferedReader buffer;
        String expr = null;
        if (args.length == 1) {
            buffer = new BufferedReader(new FileReader(args[0]));

            while ((expr = buffer.readLine()) != null) {
                System.out.print("omega> ");
                System.out.println("omega> ".concat(Omega.eval(expr)));
            }

            buffer.close();
        } else {
            buffer = new BufferedReader(new InputStreamReader(System.in));

            while (!"(stop)".equals(expr)) {
                System.out.print("omega> ");
                expr = buffer.readLine();
                System.out.println("omega> ".concat(Omega.eval(expr)));
            }
        }
    }
}
