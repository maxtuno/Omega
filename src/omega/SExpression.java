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
public final class SExpression implements Comparable {

    SExpression car;
    SExpression cdr;
    boolean atom;
    boolean number;
    String toString;
    BigInteger toNumber;
    Stack stack;
    StringBuffer buffer;

    public SExpression(BigInteger num) {
        car = this;
        cdr = this;
        atom = true;
        number = true;
        toString = num.toString();
        toNumber = num;
        stack = new Stack();
        bind(this);
    }

    public SExpression(String expr) {
        car = this;
        cdr = this;
        atom = true;
        number = false;
        toString = expr;
        toNumber = BigInteger.valueOf(0);
        stack = new Stack();
        bind(this);
    }

    public SExpression(SExpression head, SExpression tail) {
        car = head;
        cdr = tail;
        atom = false;
        number = false;
        toString = "";
        toNumber = BigInteger.valueOf(0);
        stack = null;
    }

    public SExpression cons(SExpression tail) {
        if (tail.isAtom() && (tail != Atom.NIL)) {
            return this;
        }

        return new SExpression(this, tail);
    }

    public SExpression car() {
        return car;
    }

    public SExpression cdr() {
        return cdr;
    }

    public SExpression cadr() {
        return cdr.car;
    }

    public SExpression caddr() {
        return cdr.cdr.car;
    }

    public SExpression cadddr() {
        return cdr.cdr.cdr.car;
    }

    public SExpression length() {
        long l = 0;
        SExpression expr = this;

        while (!expr.isAtom()) {
            l++;
            expr = expr.cdr();
        }

        return new SExpression(BigInteger.valueOf(l));
    }

    public SExpression size() {
        return new SExpression(BigInteger.valueOf(toString().length()));
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

    public void bind(SExpression expr) {
        stack.push(expr);
    }

    public SExpression getValue() {
        return (SExpression) stack.peek();
    }

    public void unbind() {
        stack.pop();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SExpression) {
            SExpression expr = (SExpression) obj;

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

    void toString(SExpression expr) {
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
        SExpression expr = (SExpression) obj;

        return toNumber.compareTo(expr.toNumber);
    }
}
