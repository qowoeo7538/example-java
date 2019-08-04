package org.lucas.example.base.kata.function.impl;

@FunctionalInterface
public interface Function<T, R> {
    R apply(T a, T b);
}
