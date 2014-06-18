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

import java.util.Objects;
import java.util.Stack;

/**
 *
 * @author O. A. Riveros
 */
public final class OmegaSExpression implements Comparable {

    OmegaSExpression car;
    OmegaSExpression cdr;
    boolean atom;
    boolean number;
    String toString;
    BigInteger toNumber;
    Stack stack;
    StringBuffer buffer;

    public OmegaSExpression(BigInteger num) {
        car = this;
        cdr = this;
        atom = true;
        number = true;
        toString = num.toString();
        toNumber = num;
        stack = new Stack();
        bind(this);
    }

    public OmegaSExpression(String expr) {
        car = this;
        cdr = this;
        atom = true;
        number = false;
        toString = expr;
        toNumber = BigInteger.valueOf(0);
        stack = new Stack();
        bind(this);
    }

    public OmegaSExpression(OmegaSExpression head, OmegaSExpression tail) {
        car = head;
        cdr = tail;
        atom = false;
        number = false;
        toString = "";
        toNumber = BigInteger.valueOf(0);
        stack = null;
    }

    public OmegaSExpression cons(OmegaSExpression tail) {
        if (tail.isAtom() && (tail != OmegaAtom.NIL)) {
            return this;
        }

        return new OmegaSExpression(this, tail);
    }

    public OmegaSExpression car() {
        return car;
    }

    public OmegaSExpression cdr() {
        return cdr;
    }

    public OmegaSExpression cadr() {
        return cdr.car;
    }

    public OmegaSExpression caddr() {
        return cdr.cdr.car;
    }

    public OmegaSExpression cadddr() {
        return cdr.cdr.cdr.car;
    }

    public OmegaSExpression length() {
        long l = 0;
        OmegaSExpression expr = this;

        while (!expr.isAtom()) {
            l++;
            expr = expr.cdr();
        }

        return new OmegaSExpression(BigInteger.valueOf(l));
    }

    public OmegaSExpression size() {
        return new OmegaSExpression(BigInteger.valueOf(toString().length()));
    }

    public boolean isAtom() {
        return atom;
    }

    public boolean isNumber() {
        return number;
    }

    public boolean isBound() {
        return !stack.isEmpty();
    }

    public void bind(OmegaSExpression expr) {
        stack.push(expr);
    }

    public OmegaSExpression getValue() {
        return (OmegaSExpression) stack.peek();
    }

    public void unbind() {
        stack.pop();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OmegaSExpression) {
            OmegaSExpression expr = (OmegaSExpression) obj;

            if (number && expr.number) {
                return toNumber.equals(expr.toNumber);
            }

            if (number || expr.number) {
                return false;
            }

            if (atom && expr.atom) {
                return this == expr;
            }

            if (atom || expr.atom) {
                return false;
            }

            return car.equals(expr.car) && cdr.equals(expr.cdr);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 41 * hash + Objects.hashCode(this.car);
        hash = 41 * hash + Objects.hashCode(this.cdr);
        hash = 41 * hash + (this.atom
                ? 1
                : 0);
        hash = 41 * hash + (this.number
                ? 1
                : 0);
        hash = 41 * hash + Objects.hashCode(this.toNumber);

        return hash;
    }

    @Override
    public String toString() {
        buffer = new StringBuffer();
        toString(this);

        return buffer.toString();
    }

    void toString(OmegaSExpression expr) {
        if (expr.isAtom() && !expr.toString.equals("")) {
            buffer.append(expr.toString);

            return;
        }

        buffer.append('(');

        while (!expr.isAtom()) {
            toString(expr.car);
            expr = expr.cdr;

            if (!expr.isAtom()) {
                buffer.append(' ');
            }
        }

        buffer.append(')');
    }

    @Override
    public int compareTo(Object obj) {
        OmegaSExpression expr = (OmegaSExpression) obj;

        return toNumber.compareTo(expr.toNumber);
    }
}
