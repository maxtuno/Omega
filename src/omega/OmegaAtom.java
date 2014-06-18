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
public class OmegaAtom {

    static Map<String, OmegaSExpression> atoms = new HashMap();

    public static final OmegaSExpression NIL = makeAtom("");
    public static final OmegaSExpression NILnil = makeAtom("nil");

    public static final OmegaSExpression LEFT_PARENTHESIS = makeAtom("(");
    public static final OmegaSExpression RIGHT_PARENTHESIS = makeAtom(")");

    public static final OmegaSExpression QUOTE = makeAtom("'");

    public static final OmegaSExpression IF = makeAtom("if");

    public static final OmegaSExpression LAMBDA = makeAtom("lambda");

    public static final OmegaSExpression TRUE = makeAtom("true");
    public static final OmegaSExpression FALSE = makeAtom("false");

    public static final OmegaSExpression CAR = makeAtom("car");
    public static final OmegaSExpression CDR = makeAtom("cdr");
    public static final OmegaSExpression CONS = makeAtom("cons");

    public static final OmegaSExpression ATOM = makeAtom("atom");
    public static final OmegaSExpression EQUALS = makeAtom("=");

    public static final OmegaSExpression PLUS = makeAtom("+");
    public static final OmegaSExpression MINUS = makeAtom("-");
    public static final OmegaSExpression TIMES = makeAtom("*");
    public static final OmegaSExpression TO_THE_POWER = makeAtom("^");

    public static final OmegaSExpression LESS_THAN_OR_EQUAL_TO = makeAtom("<=");
    public static final OmegaSExpression GREATER_THAN_OR_EQUAL_TO = makeAtom(">=");
    public static final OmegaSExpression LESS_THAN = makeAtom("<");
    public static final OmegaSExpression GREATER_THAN = makeAtom(">");

    public static final OmegaSExpression SIZE = makeAtom("size");
    public static final OmegaSExpression LENGTH = makeAtom("length");

    public static final OmegaSExpression EVAL = makeAtom("eval");

    public static final OmegaSExpression DEFINE = makeAtom("define");

    public static OmegaSExpression makeAtom(String expr) {
        OmegaSExpression atom = (OmegaSExpression) atoms.get(expr);
        if (atom == null) {
            if (isNumber(expr)) {
                atom = new OmegaSExpression(new BigInteger(expr));
            } else {
                atom = new OmegaSExpression(expr);
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

    static boolean isComment(OmegaSExpression expr) {
        return expr.toString().startsWith(";");
    }

    public static void refreshBindings() {
        atoms.values().stream().forEach((atom) -> {
            atom.bind(atom);
        });
        OmegaAtom.NILnil.unbind();
        OmegaAtom.NILnil.bind(OmegaAtom.NIL);
    }

    public static void restoreBindings() {
        atoms.values().stream().map((OmegaSExpression atom) -> {
            atom.unbind();
            return atom;
        }).filter((atom) -> (!atom.isBound())).forEach((atom) -> {
            atom.bind(atom);
        });
    }

}
