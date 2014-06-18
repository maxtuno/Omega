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

    public OmegaSExpression parse(String expr) {
        tokenizer = new StringTokenizer(expr.trim(), " ()", true);

        return get();
    }

    OmegaSExpression get() {
        String next = nextToken();
        OmegaSExpression e = OmegaAtom.makeAtom(next);

        if (e == OmegaAtom.LEFT_PARENTHESIS) {
            return getList();
        }

        return e;
    }

    OmegaSExpression getList() {
        OmegaSExpression v = get();

        if (v == OmegaAtom.RIGHT_PARENTHESIS) {
            return OmegaAtom.NIL;
        }

        OmegaSExpression w = getList();

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
