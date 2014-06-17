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
public class OmegaInterpreter {

    static Map<String, SExpression> define = new HashMap();

    public OmegaInterpreter() {
        Atom.NILnil.bind(Atom.NIL);
    }

    public SExpression eval(SExpression expr) {

        if (define.containsKey(expr.toString())) {
            return define.get(expr.toString());
        }
        
        if (Atom.isComment(expr)) {
            return Atom.NIL;
        }

        if (expr.isAtom()) {
            return expr.getValue();
        }

        if (expr.isAtom()) {
            return expr.getValue();
        }

        SExpression function = eval(expr.car());

        if (function == Atom.QUOTE) {
            return expr.cadr();
        }

        if (function == Atom.IF) {
            SExpression predicate = eval(expr.cadr());

            return (predicate == Atom.FALSE)
                    ? eval(expr.cadddr())
                    : eval(expr.caddr());
        }

        SExpression args = evalList(expr.cdr());
        SExpression x = args.car();
        SExpression y = args.cadr();

        if (function == Atom.SIZE) {
            return x.size();
        }

        if (function == Atom.LENGTH) {
            return x.length();
        }

        if (function == Atom.ATOM) {
            return x.isAtom()
                    ? Atom.TRUE
                    : Atom.FALSE;
        }

        if (function == Atom.CAR) {
            return x.car();
        }

        if (function == Atom.CDR) {
            return x.cdr();
        }

        if (function == Atom.CONS) {
            return x.cons(y);
        }

        if (function == Atom.PLUS) {
            return new SExpression(x.toNumber.add(y.toNumber));
        }

        if (function == Atom.MINUS) {
            return new SExpression(BigInteger.valueOf(0).max(x.toNumber.subtract(y.toNumber)));
        }

        if (function == Atom.TIMES) {
            return new SExpression(x.toNumber.multiply(y.toNumber));
        }

        if (function == Atom.TO_THE_POWER) {
            return new SExpression(x.toNumber.pow(y.toNumber.intValue()));
        }

        if (function == Atom.EQUALS) {
            return x.equals(y)
                    ? Atom.TRUE
                    : Atom.FALSE;
        }

        if (function == Atom.LESS_THAN_OR_EQUAL_TO) {
            return (x.compareTo(y) != 1)
                    ? Atom.TRUE
                    : Atom.FALSE;
        }

        if (function == Atom.GREATER_THAN_OR_EQUAL_TO) {
            return (x.compareTo(y) != -1)
                    ? Atom.TRUE
                    : Atom.FALSE;
        }

        if (function == Atom.LESS_THAN) {
            return (x.compareTo(y) == -1)
                    ? Atom.TRUE
                    : Atom.FALSE;
        }

        if (function == Atom.GREATER_THAN) {
            return (x.compareTo(y) == 1)
                    ? Atom.TRUE
                    : Atom.FALSE;
        }

        if (function == Atom.EVAL) {
            Atom.refreshBindings();

            SExpression val = eval(x);

            Atom.restoreBindings();

            return val;
        }

        if (function == Atom.DEFINE) {
            Atom.refreshBindings();
            SExpression val;

            if (!define.containsKey(x.toString())) {
                define.put(x.toString(), y);
            } else {
                define.replace(x.toString(), y);
            }

            Atom.restoreBindings();

            return Atom.TRUE;
        }

        SExpression vars = function.cadr();
        SExpression body = function.caddr();

        bind(vars, args);

        SExpression val = eval(body);

        unbind(vars);

        return val;
    }

    SExpression evalList(SExpression expr) {
        if (expr.isAtom()) {
            return Atom.NIL;
        }

        SExpression h = eval(expr.car());
        SExpression t = evalList(expr.cdr());

        return h.cons(t);
    }

    void bind(SExpression vars, SExpression args) {
        if (vars.isAtom()) {
            return;
        }

        bind(vars.cdr(), args.cdr());

        if (vars.car().isAtom() && !vars.car().isNumber()) {
            vars.car().bind(args.car());
        }
    }

    void unbind(SExpression vars) {
        if (vars.isAtom()) {
            return;
        }

        if (vars.car().isAtom() && !vars.car().isNumber()) {
            vars.car().unbind();
        }

        unbind(vars.cdr());
    }
}
