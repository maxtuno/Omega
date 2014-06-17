/*
 * Copyright 2014 O. A. Riveros. All rights reserved.
 *
 * The use of any part of this software, it always allowed and where nonprofit,
 * whether to use the software for profit contact me to oscar.riveros@gmail.com
 * and let's talk about a commercial license.
 *
 */
package omega;

import java.util.StringTokenizer;

/**
 *
 * @author O. A. Riveros
 */
public class OmegaParser {

    StringTokenizer tokenizer;

    public SExpression parse(String expr) {
        tokenizer = new StringTokenizer(expr.trim(), " ()", true);

        return get();
    }

    SExpression get() {
        String next = nextToken();
        SExpression e = Atom.makeAtom(next);

        if (e == Atom.LEFT_PARENTHESIS) {
            return getList();
        }

        return e;
    }

    SExpression getList() {
        SExpression v = get();

        if (v == Atom.RIGHT_PARENTHESIS) {
            return Atom.NIL;
        }

        SExpression w = getList();

        return v.cons(w);
    }

    String nextToken() {
        String next;

        do {
            next = tokenizer.nextToken();
        } while (next.equals(" "));

        return next;
    }
}
