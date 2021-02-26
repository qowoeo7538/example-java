package org.lucas.example.foundation.basic.kata.generics.impl;

public class Teacher<T, U> implements Person<T> {

    private T name;

    private U level;

    public Teacher() {
    }

    public Teacher(T name, U level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public T getMessage() {
        return name;
    }
}
