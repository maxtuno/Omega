/*
 * Copyright 2014 O. A. Riveros. All rights reserved.
 *
 * The use of any part of this software, it always allowed and where nonprofit,
 * whether to use the software for profit contact me to oscar.riveros@gmail.com
 * and let's talk about a commercial license.
 *
 */
package omega;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author O. A. Riveros
 */
public class Atom {

    static Map<String, SExpression> atoms = new HashMap();

    public static final SExpression NIL = makeAtom("");
    public static final SExpression NILnil = makeAtom("nil");

    public static final SExpression LEFT_PARENTHESIS = makeAtom("(");
    public static final SExpression RIGHT_PARENTHESIS = makeAtom(")");

    public static final SExpression QUOTE = makeAtom("'");

    public static final SExpression IF = makeAtom("if");

    public static final SExpression LAMBDA = makeAtom("lambda");

    public static final SExpression TRUE = makeAtom("true");
    public static final SExpression FALSE = makeAtom("false");

    public static final SExpression CAR = makeAtom("car");
    public static final SExpression CDR = makeAtom("cdr");
    public static final SExpression CONS = makeAtom("cons");

    public static final SExpression ATOM = makeAtom("atom");
    public static final SExpression EQUALS = makeAtom("=");

    public static final SExpression PLUS = makeAtom("+");
    public static final SExpression MINUS = makeAtom("-");
    public static final SExpression TIMES = makeAtom("*");
    public static final SExpression TO_THE_POWER = makeAtom("^");

    public static final SExpression LESS_THAN_OR_EQUAL_TO = makeAtom("<=");
    public static final SExpression GREATER_THAN_OR_EQUAL_TO = makeAtom(">=");
    public static final SExpression LESS_THAN = makeAtom("<");
    public static final SExpression GREATER_THAN = makeAtom(">");

    public static final SExpression SIZE = makeAtom("size");
    public static final SExpression LENGTH = makeAtom("length");

    public static final SExpression EVAL = makeAtom("eval");

    public static final SExpression DEFINE = makeAtom("define");

    public static SExpression makeAtom(String expr) {
        SExpression atom = (SExpression) atoms.get(expr);
        if (atom == null) {
            if (isNumber(expr)) {
                atom = new SExpression(new BigInteger(expr));
            } else {
                atom = new SExpression(expr);
                atoms.put(expr, atom);
            }
        }
        return atom;
    }

    static boolean isNumber(String expr) {
        if (expr.length() == 0) {
            return false;
        }
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    static boolean isComment(SExpression expr) {
        return expr.toString().startsWith(";");
    }

    public static void refreshBindings() {
        atoms.values().stream().forEach((atom) -> {
            atom.bind(atom);
        });
        Atom.NILnil.unbind();
        Atom.NILnil.bind(Atom.NIL);
    }

    public static void restoreBindings() {
        atoms.values().stream().map((SExpression atom) -> {
            atom.unbind();
            return atom;
        }).filter((atom) -> (!atom.isBound())).forEach((atom) -> {
            atom.bind(atom);
        });
    }

}
