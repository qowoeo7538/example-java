package org.shaw.generic.impl;

/**
 * 集合的泛型，编译之后泛型将不存在，可以通过反射绕过编译。
 */
public class Pinter<K, T> implements GenericInf<T> {
    private K x;
    private T y;

    public Pinter() {
    }

    public Pinter(K x, T y) {
        this.x = x;
        this.y = y;
    }

    public K getX() {
        return x;
    }

    public void setX(K x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    @Override
    public void pinter() {
        System.out.println("x: " + x + ",y: " + y);
    }

    @Override
    public String toString() {
        return "x=" + getX() + ",y=" + getY();
    }
}
