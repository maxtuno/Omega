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
public class OmegaRuntime {

    static Map<String, OmegaSExpression> define = new HashMap();

    public OmegaRuntime() {
        OmegaAtom.NILnil.push(OmegaAtom.NIL);
    }

    public OmegaSExpression eval(OmegaSExpression expr) {

        if (define.containsKey(expr.toString())) {
            return define.get(expr.toString());
        }
        
        if (OmegaAtom.isComment(expr)) {
            return OmegaAtom.NIL;
        }

        if (expr.isAtom()) {
            return expr.getValue();
        }

        if (expr.isAtom()) {
            return expr.getValue();
        }

        OmegaSExpression function = eval(expr.car());

        if (function == OmegaAtom.QUOTE) {
            return expr.cadr();
        }

        if (function == OmegaAtom.IF) {
            OmegaSExpression predicate = eval(expr.cadr());

            return (predicate == OmegaAtom.FALSE)
                    ? eval(expr.cadddr())
                    : eval(expr.caddr());
        }

        OmegaSExpression args = evalList(expr.cdr());
        OmegaSExpression x = args.car();
        OmegaSExpression y = args.cadr();

        if (function == OmegaAtom.SIZE) {
            return x.size();
        }

        if (function == OmegaAtom.LENGTH) {
            return x.length();
        }

        if (function == OmegaAtom.ATOM) {
            return x.isAtom()
                    ? OmegaAtom.TRUE
                    : OmegaAtom.FALSE;
        }

        if (function == OmegaAtom.CAR) {
            return x.car();
        }

        if (function == OmegaAtom.CDR) {
            return x.cdr();
        }

        if (function == OmegaAtom.CONS) {
            return x.cons(y);
        }

        if (function == OmegaAtom.PLUS) {
            return new OmegaSExpression(x.toNumber.add(y.toNumber));
        }

        if (function == OmegaAtom.MINUS) {
            return new OmegaSExpression(BigInteger.valueOf(0).max(x.toNumber.subtract(y.toNumber)));
        }

        if (function == OmegaAtom.TIMES) {
            return new OmegaSExpression(x.toNumber.multiply(y.toNumber));
        }

        if (function == OmegaAtom.TO_THE_POWER) {
            return new OmegaSExpression(x.toNumber.pow(y.toNumber.intValue()));
        }

        if (function == OmegaAtom.EQUALS) {
            return x.equals(y)
                    ? OmegaAtom.TRUE
                    : OmegaAtom.FALSE;
        }

        if (function == OmegaAtom.LESS_THAN_OR_EQUAL_TO) {
            return (x.compareTo(y) != 1)
                    ? OmegaAtom.TRUE
                    : OmegaAtom.FALSE;
        }

        if (function == OmegaAtom.GREATER_THAN_OR_EQUAL_TO) {
            return (x.compareTo(y) != -1)
                    ? OmegaAtom.TRUE
                    : OmegaAtom.FALSE;
        }

        if (function == OmegaAtom.LESS_THAN) {
            return (x.compareTo(y) == -1)
                    ? OmegaAtom.TRUE
                    : OmegaAtom.FALSE;
        }

        if (function == OmegaAtom.GREATER_THAN) {
            return (x.compareTo(y) == 1)
                    ? OmegaAtom.TRUE
                    : OmegaAtom.FALSE;
        }

        if (function == OmegaAtom.EVAL) {
            OmegaAtom.refreshBindings();

            OmegaSExpression val = eval(x);

            OmegaAtom.restoreBindings();

            return val;
        }

        if (function == OmegaAtom.DEFINE) {
            OmegaAtom.refreshBindings();
            OmegaSExpression val;

            if (!define.containsKey(x.toString())) {
                define.put(x.toString(), y);
            } else {
                define.replace(x.toString(), y);
            }

            OmegaAtom.restoreBindings();

            return OmegaAtom.TRUE;
        }

        OmegaSExpression vars = function.cadr();
        OmegaSExpression body = function.caddr();

        bind(vars, args);

        OmegaSExpression val = eval(body);

        unbind(vars);

        return val;
    }

    OmegaSExpression evalList(OmegaSExpression expr) {
        if (expr.isAtom()) {
            return OmegaAtom.NIL;
        }

        OmegaSExpression h = eval(expr.car());
        OmegaSExpression t = evalList(expr.cdr());

        return h.cons(t);
    }

    void bind(OmegaSExpression vars, OmegaSExpression args) {
        if (vars.isAtom()) {
            return;
        }

        bind(vars.cdr(), args.cdr());

        if (vars.car().isAtom() && !vars.car().isNumber()) {
            vars.car().push(args.car());
        }
    }

    void unbind(OmegaSExpression vars) {
        if (vars.isAtom()) {
            return;
        }

        if (vars.car().isAtom() && !vars.car().isNumber()) {
            vars.car().pop();
        }

        unbind(vars.cdr());
    }
}
